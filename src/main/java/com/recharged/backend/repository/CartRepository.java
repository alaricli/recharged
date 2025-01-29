package com.recharged.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.recharged.backend.entity.Cart;
import com.recharged.backend.entity.Customer;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
  Cart findByCustomer(Customer customer);
}
