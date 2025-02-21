package com.recharged.backend.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.recharged.backend.dto.CheckoutIntentDTO;
import com.recharged.backend.entity.Cart;
import com.recharged.backend.entity.CartItem;
import com.recharged.backend.entity.CustomerOrder;
import com.recharged.backend.repository.CartRepository;
import com.recharged.backend.repository.CustomerOrderRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

import jakarta.annotation.PostConstruct;

@Service
public class CheckoutService {
  @Value("${stripe.secret}")
  private String stripeApiKey;

  @PostConstruct
  public void init() {
    Stripe.apiKey = stripeApiKey;
  }

  private final CustomerOrderRepository customerOrderRepository;
  private final CartRepository cartRepository;

  public CheckoutService(CustomerOrderRepository customerOrderRepository,
      CartRepository cartRepository) {
    this.customerOrderRepository = customerOrderRepository;
    this.cartRepository = cartRepository;
  }

  // attach customer information such as contact and address to the order for now
  // mark orderstatus as 'created' or 'unpaid' for now
  // update order status once paid using callback from stripe api webhook
  public CustomerOrder createOrder(Long cartId) {
    CustomerOrder newCustomerOrder = new CustomerOrder();
    newCustomerOrder.setOrderStatus("created");
    return customerOrderRepository.save(newCustomerOrder);
  }

  public CustomerOrder editOrder(CheckoutIntentDTO requestDTO) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  // create Stripe CheckoutIntent
  // needs products (line items), accepted payment methods
  public String createStripeCheckout(Long cartId) throws StripeException {
    // helper to extract items using request.orderId
    // another helper to convert each item to a line item
    // add each line item

    Cart cart = cartRepository.findById(cartId)
        .orElseThrow(() -> new IllegalArgumentException("Cart not found for ID: " + cartId));

    List<SessionCreateParams.LineItem> lineItems = new ArrayList<>();

    for (CartItem cartItem : cart.getCartItems()) {
      SessionCreateParams.LineItem lineItem = createLineItem(cartItem);
      lineItems.add(lineItem);
    }

    SessionCreateParams params = SessionCreateParams.builder()
        .setSuccessUrl("https://example.com/success")
        .setCancelUrl("https://localhost:3000")
        .addAllLineItem(lineItems)
        .setMode(SessionCreateParams.Mode.PAYMENT)
        .build();

    Session session = Session.create(params);
    CustomerOrder order = createOrder(cartId);
    order.setStripeSessionId(session.getId());
    order.setOrderStatus("created");
    customerOrderRepository.save(order);
    return session.getUrl();
  }

  // helper to convert CartItem -> LineItem
  private SessionCreateParams.LineItem createLineItem(CartItem cartItem) {
    return SessionCreateParams.LineItem.builder()
        .setPrice(cartItem.getProduct().getStripePriceId())
        .setQuantity(cartItem.getQuantity())
        .build();
  }
}
