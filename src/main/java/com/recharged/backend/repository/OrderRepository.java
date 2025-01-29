package com.recharged.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.recharged.backend.entity.Customer;
import com.recharged.backend.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
  List<Order> findOrdersByCustomer(Customer customer);

}
