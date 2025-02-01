package com.recharged.backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.recharged.backend.dto.CheckoutRequestDTO;
import com.recharged.backend.repository.CustomerOrderRepository;
import com.stripe.Stripe;

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

  public CheckoutService(CustomerOrderRepository customerOrderRepository) {
    this.customerOrderRepository = customerOrderRepository;
  }

  //
  // create checkout session
  public void createStripeCheckoutSession(CheckoutRequestDTO requestDTO) {

  }

  // helper
  // create order from cart
  public void createOrderFromCart(CheckoutRequestDTO requestDTO) {
    // TODO: Implement the logic to create an order from checkout request

  }

  // helper
  // change order status

  // helper
  // save order
}
