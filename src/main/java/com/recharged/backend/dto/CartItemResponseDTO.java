package com.recharged.backend.dto;

import java.math.BigDecimal;

import com.recharged.backend.entity.CartItem;

public class CartItemResponseDTO {
  private Long id;
  private String sku;
  private String name;
  private String imageUrl;
  private int quantity;
  private BigDecimal price;

  public CartItemResponseDTO(CartItem cartItem) {
    this.id = cartItem.getId();
    this.sku = cartItem.getProduct().getSku();
    this.name = cartItem.getProduct().getName();
    this.imageUrl = cartItem.getProduct().getMainImage(); // Ensure Product has
    this.quantity = cartItem.getQuantity();
    this.price = cartItem.getProduct().getUnitPriceCAD();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getSku() {
    return sku;
  }

  public void setSku(String sku) {
    this.sku = sku;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

}