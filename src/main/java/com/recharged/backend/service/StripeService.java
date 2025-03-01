package com.recharged.backend.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.recharged.backend.entity.Cart;
import com.recharged.backend.entity.CartItem;
import com.recharged.backend.entity.LocalUser;
import com.recharged.backend.entity.Product;
import com.recharged.backend.repository.CartRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Price;
import com.stripe.model.checkout.Session;
import com.stripe.param.PriceCreateParams;
import com.stripe.param.ProductCreateParams;
import com.stripe.param.checkout.SessionCreateParams;

import jakarta.annotation.PostConstruct;

@Service
public class StripeService {
  @Value("${stripe.secret}")
  private String stripeApiKey;

  private final CartRepository cartRepository;
  private final OrderService orderService;

  public StripeService(CartRepository cartRepository, OrderService orderService) {
    this.cartRepository = cartRepository;
    this.orderService = orderService;
  }

  @PostConstruct
  public void init() {
    Stripe.apiKey = stripeApiKey;
  }

  // Product management methods
  public void createStripeProduct(Product product) throws StripeException {
    ProductCreateParams params = ProductCreateParams.builder()
        .setName(product.getName())
        .build();

    com.stripe.model.Product stripeProduct = com.stripe.model.Product.create(params);
    product.setStripeProductId(stripeProduct.getId());
  }

  public void createStripePriceObject(Product product) throws StripeException {
    if (product.getUnitAmount() == null || product.getUnitAmount() <= 0) {
      throw new IllegalArgumentException("Invalid unit amount for product: " + product.getName());
    }
    if (product.getStripeProductId() == null || product.getStripeProductId().isEmpty()) {
      throw new IllegalArgumentException("Stripe Product ID is missing for product: " + product.getName());
    }

    PriceCreateParams params = PriceCreateParams.builder()
        .setCurrency("cad")
        .setUnitAmount(product.getUnitAmount())
        .setProduct(product.getStripeProductId())
        .build();

    try {
      Price createdPrice = Price.create(params);
      product.setStripePriceId(createdPrice.getId());
    } catch (StripeException e) {
      String errorMsg = "Failed to create Stripe price for product: " + product.getName();
      System.err.println(errorMsg + " - " + e.getMessage());
      throw new RuntimeException(errorMsg, e);
    }
  }

  // Checkout methods
  public String createStripeCheckoutForUser(LocalUser user) throws StripeException {
    Cart cart = cartRepository.findByUser(user);
    if (cart == null || cart.getCartItems().isEmpty()) {
      throw new IllegalArgumentException("Cart is empty or not found");
    }

    List<SessionCreateParams.LineItem> lineItems = new ArrayList<>();
    for (CartItem cartItem : cart.getCartItems()) {
      lineItems.add(createLineItem(cartItem));
    }

    SessionCreateParams params = SessionCreateParams.builder()
        .setSuccessUrl("https://example.com/success")
        .setCancelUrl("https://localhost:3000")
        .addAllLineItem(lineItems)
        .setMode(SessionCreateParams.Mode.PAYMENT)
        .build();

    Session session = Session.create(params);

    // Create order using OrderService
    orderService.createOrderFromCart(cart, session.getId());

    return session.getUrl();
  }

  // Helper methods
  private SessionCreateParams.LineItem createLineItem(CartItem cartItem) {
    return SessionCreateParams.LineItem.builder()
        .setPrice(cartItem.getProduct().getStripePriceId())
        .setQuantity(cartItem.getQuantity())
        .build();
  }
}
