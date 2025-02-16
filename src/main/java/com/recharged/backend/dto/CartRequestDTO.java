package com.recharged.backend.dto;

import java.util.List;

public class CartRequestDTO {
  private CustomerRequestDTO customer;

  public CustomerRequestDTO getCustomer() {
    return customer;
  }

  public void setCustomer(CustomerRequestDTO customer) {
    this.customer = customer;
  }

}
