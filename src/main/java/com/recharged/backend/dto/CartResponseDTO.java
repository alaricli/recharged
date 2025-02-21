package com.recharged.backend.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.recharged.backend.entity.Cart;

public class CartResponseDTO {
  private Long cartId;
  private List<CartItemResponseDTO> items;
  private LocalDateTime lastUpdated;
  private Long cartSubTotal;

  public CartResponseDTO(Cart cart) {
    this.cartId = cart.getId();
    this.items = cart.getCartItems().stream()
        .map(CartItemResponseDTO::new)
        .collect(Collectors.toList());
    this.lastUpdated = cart.getLastUpdatedDateTime();
    this.cartSubTotal = cart.getCartSubTotal();
  }

  public Long getCartId() {
    return cartId;
  }

  public List<CartItemResponseDTO> getItems() {
    return items;
  }

  public LocalDateTime getLastUpdated() {
    return lastUpdated;
  }

  public void setCartId(Long cartId) {
    this.cartId = cartId;
  }

  public void setItems(List<CartItemResponseDTO> items) {
    this.items = items;
  }

  public void setLastUpdated(LocalDateTime lastUpdated) {
    this.lastUpdated = lastUpdated;
  }

  public Long getCartSubTotal() {
    return cartSubTotal;
  }

  public void setCartSubTotal(Long cartSubTotal) {
    this.cartSubTotal = cartSubTotal;
  }

}