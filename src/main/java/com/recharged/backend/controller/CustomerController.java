package com.recharged.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.recharged.backend.dto.CustomerRequestDTO;
import com.recharged.backend.entity.Customer;
import com.recharged.backend.service.CustomerService;

@RestController("/api")
public class CustomerController {
  private final CustomerService customerService;

  public CustomerController(CustomerService customerService) {
    this.customerService = customerService;
  }

  @PostMapping("/auth/signup")
  public ResponseEntity<?> addCustomer(@RequestBody CustomerRequestDTO newCustomerDetails) {
    Customer newCustomer = customerService.addCustomer(newCustomerDetails);
    return ResponseEntity.ok().body(newCustomer);
  }

  // @PostMapping("/auth/login")
  // public ResponseEntity<?> loginCustomer(@RequestBody CustomerRequestDTO
  // customerDetails) {
  // Customer customer = customerService.loginCustomer(customerDetails);
  // return ResponseEntity.ok().body(customer);
  // }
}
