package com.recharged.backend.dto;

import java.util.List;

public class CartRequestDTO {
  private CustomerRequestDTO customer;
  private List<CartItemRequestDTO> cartItems;
  private String currency;

  public CustomerRequestDTO getCustomer() {
    return customer;
  }

  public void setCustomer(CustomerRequestDTO customer) {
    this.customer = customer;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public List<CartItemRequestDTO> getCartItems() {
    return cartItems;
  }

  public void setCartItems(List<CartItemRequestDTO> cartItems) {
    this.cartItems = cartItems;
  }

}
