package com.recharged.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.recharged.backend.entity.Cart;

public class CartResponseDTO {
  private Long cartId;
  private List<CartItemResponseDTO> items;
  private BigDecimal totalPrice;
  private LocalDateTime lastUpdated;

  public CartResponseDTO(Cart cart) {
    this.cartId = cart.getId();
    this.items = cart.getCartItems().stream()
        .map(CartItemResponseDTO::new)
        .collect(Collectors.toList());
    this.totalPrice = calculateTotal(cart);
    this.lastUpdated = cart.getLastUpdatedDateTime();
  }

  private BigDecimal calculateTotal(Cart cart) {
    return cart.getCartItems().stream()
        .map(item -> item.getProduct().getUnitPriceCAD().multiply(BigDecimal.valueOf(item.getQuantity())))
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  public Long getCartId() {
    return cartId;
  }

  public List<CartItemResponseDTO> getItems() {
    return items;
  }

  public BigDecimal getTotalPrice() {
    return totalPrice;
  }

  public LocalDateTime getLastUpdated() {
    return lastUpdated;
  }
}