package com.recharged.backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.recharged.backend.dto.ProductRequestDTO;
import com.recharged.backend.dto.StripePriceRequestDTO;
import com.recharged.backend.entity.StripePriceObject;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Price;
import com.stripe.model.Product;
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
  public String createStripeProduct(ProductRequestDTO requestDTO) throws StripeException {
    ProductCreateParams params = ProductCreateParams.builder()
        .setName(requestDTO.getProductName())
        .build();

    Product product = Product.create(params);
    return product.getId();
  }

  // takes in priceobjectrequest
  // returns the price id that is made
  public StripePriceObject createStripePriceObject(StripePriceRequestDTO requestDTO, String stripeProductId)
      throws StripeException {
    PriceCreateParams params = PriceCreateParams.builder()
        .setCurrency(requestDTO.getCurrency())
        .setUnitAmount(requestDTO.getUnitAmount())
        .setProduct(stripeProductId)
        .build();

    Price price = Price.create(params);
    StripePriceObject newStripePriceObject = mapPriceObject(price);
    return newStripePriceObject;
  }

  private StripePriceObject mapPriceObject(Price stripePriceObject) {
    StripePriceObject newStripePriceObject = new StripePriceObject();
    newStripePriceObject.setStripePriceId(stripePriceObject.getId());
    newStripePriceObject.setCurrency(stripePriceObject.getCurrency());
    newStripePriceObject.setUnitAmount(stripePriceObject.getUnitAmount());
    return newStripePriceObject;
  }
}
