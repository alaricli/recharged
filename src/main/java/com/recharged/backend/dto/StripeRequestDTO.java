package com.recharged.backend.dto;

public class StripeRequestDTO {
  private Long orderId;
  private CartRequestDTO cartRequestDTO;

  public Long getOrderId() {
    return orderId;
  }

  public void setOrderId(Long orderId) {
    this.orderId = orderId;
  }

  public CartRequestDTO getCartRequestDTO() {
    return cartRequestDTO;
  }

  public void setCartRequestDTO(CartRequestDTO cartRequestDTO) {
    this.cartRequestDTO = cartRequestDTO;
  }

}
