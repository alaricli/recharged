package com.recharged.backend.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.recharged.backend.dto.CheckoutItemDTO;
import com.recharged.backend.dto.CheckoutRequestDTO;
import com.recharged.backend.entity.CustomerOrder;
import com.recharged.backend.repository.CustomerOrderRepository;
import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.stripe.exception.StripeException;

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

  // create checkout session
  public String createStripeCheckoutSession(CheckoutRequestDTO requestDTO) throws StripeException {
    List<SessionCreateParams.LineItem> lineItems = new ArrayList<>();

    for (CheckoutItemDTO item : requestDTO.getItems()) {
      SessionCreateParams.LineItem.PriceData priceData = SessionCreateParams.LineItem.PriceData.builder()
          .setCurrency(item.getCurrency())
          .build();

      SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem.builder()
          .setQuantity((long) item.getQuantity())
          .setPriceData(priceData)
          .build();
      lineItems.add(lineItem);
    }

    SessionCreateParams params = SessionCreateParams.builder()
        .setSuccessUrl("https://localhost:3000/checkout/success")
        .setCancelUrl("https://localhost:3000/checkout/cancel")
        .addAllLineItem(
            lineItems)
        .setMode(SessionCreateParams.Mode.PAYMENT)
        .build();

    // create a "copy" by creating an order object and save it
    // mark the status of that object according to stripe's output

    Session session = Session.create(params);
    return session.getUrl();
  }

  // helper
  // create order from cart
  public CustomerOrder createOrder(CheckoutRequestDTO requestDTO) {
    // TODO: Implement the logic to create an order from checkout request
    throw new UnsupportedOperationException("Not implemented yet");
  }

  // helper
  // change order status

  // helper
  // save order
}
