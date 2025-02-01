package com.recharged.backend.dto;

import java.math.BigDecimal;

public class CartEditRequestDTO {
  private Long productId;
  private int quantity;
  private BigDecimal price;

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

}
