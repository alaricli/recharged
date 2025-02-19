package com.recharged.backend.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.recharged.backend.dto.CartItemRequestDTO;
import com.recharged.backend.dto.CheckoutIntentDTO;
import com.recharged.backend.dto.StripeRequestDTO;
import com.recharged.backend.entity.CustomerOrder;
import com.recharged.backend.entity.Product;
import com.recharged.backend.entity.StripePriceObject;
import com.recharged.backend.repository.CustomerOrderRepository;
import com.recharged.backend.repository.ProductRepository;
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
  private final ProductRepository productRepository;

  public CheckoutService(CustomerOrderRepository customerOrderRepository, ProductRepository productRepository) {
    this.customerOrderRepository = customerOrderRepository;
    this.productRepository = productRepository;
  }

  // attach customer information such as contact and address to the order for now
  // mark orderstatus as 'created' or 'unpaid' for now
  // update order status once paid using callback from stripe api webhook
  public CustomerOrder createOrder(CheckoutIntentDTO requestDTO) {
    CustomerOrder newCustomerOrder = new CustomerOrder();
    newCustomerOrder.setOrderStatus("created");
    return customerOrderRepository.save(newCustomerOrder);
  }

  public CustomerOrder editOrder(CheckoutIntentDTO requestDTO) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  // create Stripe CheckoutIntent
  // needs products (line items), accepted payment methods
  public String createStripeCheckoutSession(StripeRequestDTO requestDTO) throws StripeException {
    // helper to extract items using request.orderId
    // another helper to convert each item to a line item
    // add each line item
    List<SessionCreateParams.LineItem> lineItems = new ArrayList<>();
    String currency = requestDTO.getCartRequestDTO().getCurrency();

    for (CartItemRequestDTO cartItemRequestDTO : requestDTO.getCartRequestDTO().getCartItems()) {
      SessionCreateParams.LineItem lineItem = createLineItem(cartItemRequestDTO, currency);
      lineItems.add(lineItem);
    }

    SessionCreateParams params = SessionCreateParams.builder()
        .setSuccessUrl("https://example.com/success")
        .setCancelUrl("https://localhost:3000")
        .setCurrency(requestDTO.getCartRequestDTO().getCurrency())
        .addAllLineItem(lineItems)
        .setMode(SessionCreateParams.Mode.PAYMENT)
        .build();

    Session session = Session.create(params);
    return session.getUrl();
  }

  // helper to convert CartItem -> LineItem
  private SessionCreateParams.LineItem createLineItem(CartItemRequestDTO cartItemRequestDTO, String currency) {
    Product product = productRepository.findBySku(cartItemRequestDTO.getSku())
        .orElseThrow(() -> new IllegalArgumentException("Product not found for SKU: " + cartItemRequestDTO.getSku()));

    String priceId = product.getStripePriceIds().stream()
        .filter(item -> item.getCurrency().equalsIgnoreCase(currency))
        .map(StripePriceObject::getStripePriceId)
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Price ID not found for the given currency: " + currency));

    return SessionCreateParams.LineItem.builder()
        .setPrice(priceId)
        .setQuantity(Long.valueOf(cartItemRequestDTO
            .getQuantity()))
        .build();
  }
}
