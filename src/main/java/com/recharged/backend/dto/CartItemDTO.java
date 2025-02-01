package com.recharged.backend.dto;

import java.math.BigDecimal;

import com.recharged.backend.entity.CartItem;

public class CartItemDTO {
  private Long productId;
  private String productName;
  private String imageUrl;
  private int quantity;
  private String currency;
  private BigDecimal price;

  public CartItemDTO(CartItem cartItem) {
    this.productId = cartItem.getProduct().getId();
    this.productName = cartItem.getProduct().getName();
    this.imageUrl = cartItem.getProduct().getProductImage(); // Ensure Product has an image field
    this.quantity = cartItem.getQuantity();
    this.price = cartItem.getProduct().getPrice();
  }

  public Long getProductId() {
    return productId;
  }

  public String getProductName() {
    return productName;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public int getQuantity() {
    return quantity;
  }

  public BigDecimal getPrice() {
    return price;
  }
}