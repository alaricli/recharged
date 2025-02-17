package com.recharged.backend.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.recharged.backend.dto.CheckoutIntentDTO;
import com.recharged.backend.dto.StripeRequestDTO;
import com.recharged.backend.entity.CustomerOrder;
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

  public CheckoutService(CustomerOrderRepository customerOrderRepository) {
    this.customerOrderRepository = customerOrderRepository;
  }

  // attach customer information such as contact and address to the order for now
  // mark orderstatus as 'created' or 'unpaid' for now
  // update order status once paid using callback from stripe api webhook
  public CustomerOrder createOrder(CheckoutIntentDTO requestDTO) {
    CustomerOrder newCustomerOrder = new CustomerOrder();
    throw new UnsupportedOperationException("Not implemented yet");
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

    SessionCreateParams params = SessionCreateParams.builder()
        .setSuccessUrl("https://example.com/success")
        .setCancelUrl("https://localhost:3000")
        .setCurrency(requestDTO.getCurrency())
        .addLineItem(
            SessionCreateParams.LineItem.builder()
                .setPrice("price_1MotwRLkdIwHu7ixYcPLm5uZ")
                .setQuantity(2L)
                .build())
        .setMode(SessionCreateParams.Mode.PAYMENT)
        .build();

    Session session = Session.create(params);
    throw new UnsupportedOperationException("Not implemented yet");
  }

  // helper to convert CartItem -> LineItem
  // for each LineItem, need to create a Price Object for it and attach it too
}
