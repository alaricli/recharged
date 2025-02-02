package com.recharged.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class CustomerOrderRequest {
  private Long customerId;
  private List<OrderItemRequestDTO> items;
  private String orderStatus;
  private LocalDateTime orderDateTime;
  private String currency;
  private BigDecimal orderTotal;

  public Long getCustomerId() {
    return customerId;
  }

  public void setCustomerId(Long customerId) {
    this.customerId = customerId;
  }

  public List<OrderItemRequestDTO> getItems() {
    return items;
  }

  public void setItems(List<OrderItemRequestDTO> items) {
    this.items = items;
  }

  public String getOrderStatus() {
    return orderStatus;
  }

  public void setOrderStatus(String orderStatus) {
    this.orderStatus = orderStatus;
  }

  public LocalDateTime getOrderDateTime() {
    return orderDateTime;
  }

  public void setOrderDateTime(LocalDateTime orderDateTime) {
    this.orderDateTime = orderDateTime;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public BigDecimal getOrderTotal() {
    return orderTotal;
  }

  public void setOrderTotal(BigDecimal orderTotal) {
    this.orderTotal = orderTotal;
  }
}
