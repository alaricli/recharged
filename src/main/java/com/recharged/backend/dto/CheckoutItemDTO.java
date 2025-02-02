package com.recharged.backend.dto;

import java.math.BigDecimal;

import com.recharged.backend.entity.CartItem;

public class CheckoutItemDTO {
  private String productId;
  private int quantity;
  private String currency;
  private BigDecimal price;

  public CheckoutItemDTO(CartItem cartItem) {
    this.productId = cartItem.getProduct().getId();
    this.currency = cartItem.getCurrency();
    this.quantity = cartItem.getQuantity();
    this.price = cartItem.getPrice();
  }

  public String getProductId() {
    return productId;
  }

  public void setProductId(String productId) {
    this.productId = productId;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

}
