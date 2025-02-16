package com.recharged.backend.dto;

import java.util.List;

public class CheckoutIntentDTO {
  private CustomerRequestDTO customerInformation;
  private List<CartItemRequestDTO> cartItems;
  private String currency;

  public CustomerRequestDTO getCustomerInformation() {
    return customerInformation;
  }

  public void setCustomerInformation(CustomerRequestDTO customerInformation) {
    this.customerInformation = customerInformation;
  }

  public List<CartItemRequestDTO> getCartItems() {
    return cartItems;
  }

  public void setCartItems(List<CartItemRequestDTO> cartItems) {
    this.cartItems = cartItems;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

}
