package com.recharged.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.recharged.backend.entity.PurchaseOrder;

public interface OrderRepository extends JpaRepository<PurchaseOrder, Long> {
  Optional<PurchaseOrder> findByStripeSessionId(String stripeSessionId);
}
