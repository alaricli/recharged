package com.recharged.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.recharged.backend.dto.CustomerAuthenticationRequestDTO;
import com.recharged.backend.dto.CustomerRegistrationRequestDTO;
import com.recharged.backend.entity.Customer;
import com.recharged.backend.service.AuthenticationService;
import com.recharged.backend.service.JwtService;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/auth")
public class CustomerController {
  private final JwtService jwtService;
  private final AuthenticationService authenticationService;

  public CustomerController(JwtService jwtService, AuthenticationService authenticationService) {
    this.jwtService = jwtService;
    this.authenticationService = authenticationService;
  }

  @PostMapping("/signup")
  public ResponseEntity<?> customerSignUp(@RequestBody CustomerRegistrationRequestDTO newCustomerDetails) {
    Customer newCustomer = authenticationService.signupCustomer(newCustomerDetails);
    return ResponseEntity.ok().body("successfully registered:" + newCustomer);
  }

  @PostMapping("/login")
  public ResponseEntity<?> customerLogIn(@RequestBody CustomerAuthenticationRequestDTO credentials) {
    Customer authenticatedCustomer = authenticationService.loginCustomer(credentials);

    String jwtToken = jwtService.generateToken(authenticatedCustomer);

    return ResponseEntity.ok().body(jwtToken);
  }

  @GetMapping("/users")
  public ResponseEntity<Customer> getCustomerInfo(@RequestHeader("Authorization") String token) {
    return ResponseEntity.ok().body(authenticationService.getCustomerInfo(token));
  }
}
