package com.recharged.backend.service;

import org.springframework.stereotype.Service;

import com.recharged.backend.repository.CustomerOrderRepository;

@Service
public class WebhookService {
  private final CustomerOrderRepository customerOrderRepository;

  public WebhookService(CustomerOrderRepository customerOrderRepository) {
    this.customerOrderRepository = customerOrderRepository;
  }

  // helper:
  // update order status and save to database
  public void updateOrder() {

  }

  // helper:
  // send notification to customer
  public void notifyCustomer() {

  }

  // helper:
  // send notification to admin
  public void notifyAdministrator() {

  }
}
