package com.recharged.backend.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.recharged.backend.dto.CustomerAuthenticationRequestDTO;
import com.recharged.backend.dto.CustomerRegistrationRequestDTO;
import com.recharged.backend.entity.Customer;
import com.recharged.backend.repository.CustomerRepository;

@Service
public class AuthenticationService {
  private final CustomerRepository customerRepository;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private final JwtService jwtService;

  public AuthenticationService(
      CustomerRepository customerRepository,
      PasswordEncoder passwordEncoder,
      AuthenticationManager authenticationManager,
      JwtService jwtService) {
    this.customerRepository = customerRepository;
    this.passwordEncoder = passwordEncoder;
    this.authenticationManager = authenticationManager;
    this.jwtService = jwtService;
  }

  public Customer signupCustomer(CustomerRegistrationRequestDTO newCustomerDetails) {
    Customer customer = new Customer();
    customer.setUsername(newCustomerDetails.getUsername());
    customer.setEmail(newCustomerDetails.getEmail());
    customer.setFirstName(null);
    customer.setLastName(null);
    customer.setPassword(passwordEncoder.encode(newCustomerDetails.getPassword()));
    customer.setPhoneNumber(newCustomerDetails.getPhoneNumber());
    return customerRepository.save(customer);
  }

  public Customer loginCustomer(CustomerAuthenticationRequestDTO customerCredentials) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(customerCredentials.getUsername(), customerCredentials.getPassword()));

    return customerRepository.findByUsername(customerCredentials.getUsername())
        .orElseThrow(() -> new IllegalArgumentException("User not found"));
  }

  public Customer findByUsername(String username) {
    return customerRepository.findByUsername(username)
        .orElseThrow(() -> new IllegalArgumentException("User not found"));
  }

  public Customer getCustomerInfo(String token) {
    String username = jwtService.extractUsername(token.replace("Bearer ", ""));
    return customerRepository.findByUsername(username)
        .orElseThrow(() -> new IllegalArgumentException("User not found"));
  }
}
