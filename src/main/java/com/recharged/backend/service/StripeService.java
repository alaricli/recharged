package com.recharged.backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.recharged.backend.entity.Product;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Price;
import com.stripe.param.PriceCreateParams;
import com.stripe.param.ProductCreateParams;

import jakarta.annotation.PostConstruct;

@Service
public class StripeService {
  @Value("${stripe.secret}")
  private String stripeApiKey;

  @PostConstruct
  public void init() {
    Stripe.apiKey = stripeApiKey;
  }

  // basically makes a copy of product for stripe
  // returns the stripe product id;
  public void createStripeProduct(Product product) throws StripeException {
    ProductCreateParams params = ProductCreateParams.builder()
        .setName(product.getName())
        .build();

    com.stripe.model.Product stripeProduct = com.stripe.model.Product.create(params);
    product.setStripeProductId(stripeProduct.getId());
  }

  // takes in priceobjectrequest
  // returns the price id that is made
  public void createStripePriceObject(Product product)
      throws StripeException {
    if (product.getUnitAmount() == null || product.getUnitAmount() <= 0) {
      throw new IllegalArgumentException("Invalid unit amount for product: " + product.getName());
    }
    if (product.getStripeProductId() == null || product.getStripeProductId().isEmpty()) {
      throw new IllegalArgumentException("Stripe Product ID is missing for product: " + product.getName());
    }

    PriceCreateParams params = PriceCreateParams.builder()
        .setCurrency("cad")
        .setUnitAmount(product.getUnitAmount())
        .setProduct(product
            .getStripeProductId())
        .build();

    try {
      Price createdPriceId = Price.create(params);
      product.setStripePriceId(createdPriceId.getId());
    } catch (StripeException e) {
      String errorMsg = "Failed to create Stripe price for product: " + product.getName();
      System.err.println(errorMsg + " - " + e.getMessage());
      throw new RuntimeException(errorMsg, e);
    }
  }
}
