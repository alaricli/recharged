package com.recharged.backend.dto;

import java.math.BigDecimal;

import com.recharged.backend.entity.CartItem;

public class CartItemResponseDTO {
  private String productId;
  private String imageUrl;
  private int quantity;
  private String currency;
  private BigDecimal price;

  // public CartItemResponseDTO(CartItem cartItem) {
  //   this.productId = cartItem.getProduct().getId();
  // this.productName = cartItem.getProduct().getProductName();
  // this.imageUrl = cartItem.getProduct().getMainImage(); // Ensure Product has an image field
  // this.quantity = cartItem.getQuantity();
  // 
  // this.price = cartItem.getProduct().getUnitPriceCAD();
  // 

  public String getProductId() {
    return productId;
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