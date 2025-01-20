package com.recharged.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.recharged.backend.dto.CustomerRequestDTO;
import com.recharged.backend.entity.Customer;
import com.recharged.backend.repository.CustomerRepository;

@Service
public class CustomerService {
  @Autowired
  private CustomerRepository customerRepository;

  public CustomerService(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  public Customer addCustomer(CustomerRequestDTO newCustomerDetails) {
    Customer customer = new Customer();
    customer.setUsername(newCustomerDetails.getUsername());
    customer.setEmail(newCustomerDetails.getEmail());
    customer.setPassword(newCustomerDetails.getPassword());
    customer.setPhoneNumber(newCustomerDetails.getPhoneNumber());
    return customerRepository.save(customer);
  }

  public Customer loginCustomer(CustomerRequestDTO customerDetails) {
    return customerRepository.findByUsername(customerDetails.getUsername()).orElse(null);
  }

  public Customer getCustomerById(Long customerId) {
    return customerRepository.findById(customerId).orElse(null);
  }
}
