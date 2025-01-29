package com.recharged.backend.service;

import org.springframework.stereotype.Service;

import com.recharged.backend.repository.OrderRepository;

@Service
public class CheckoutService {
  private final OrderRepository orderRepository;

  public CheckoutService(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }
}
