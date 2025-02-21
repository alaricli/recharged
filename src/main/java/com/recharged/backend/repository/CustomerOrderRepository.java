package com.recharged.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.recharged.backend.entity.CustomerOrder;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {
  Optional<CustomerOrder> findByStripeSessionId(String stripeSessionId);
}
