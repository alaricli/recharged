package com.recharged.backend.dto;

public class CartItemRequestDTO {
  private String sku;
  private Long quantity;

  public Long getQuantity() {
    return quantity;
  }

  public void setQuantity(Long quantity) {
    this.quantity = quantity;
  }

  public String getSku() {
    return sku;
  }

  public void setSku(String sku) {
    this.sku = sku;
  }

}
