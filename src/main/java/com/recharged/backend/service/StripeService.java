package com.recharged.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.recharged.backend.dto.ProductRequestDTO;
import com.recharged.backend.dto.StripePriceRequestDTO;
import com.recharged.backend.entity.Product;
import com.recharged.backend.entity.StripePriceObject;
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
    List<StripePriceObject> priceObjects = product.getStripePriceIds();
    String stripeProductId = product.getStripeProductId();

    for (StripePriceObject priceObject : priceObjects) {
      PriceCreateParams params = PriceCreateParams.builder()
          .setCurrency(priceObject.getCurrency())
          .setUnitAmount(priceObject.getUnitAmount())
          .setProduct(stripeProductId)
          .build();

      try {
        Price createdPrice = Price.create(params);
        priceObject.setStripePriceId(createdPrice.getId());
      } catch (StripeException e) {
        System.err.println("Failed to create Stripe price: " + e.getMessage());
        e.printStackTrace();
      }
    }
  }

}
