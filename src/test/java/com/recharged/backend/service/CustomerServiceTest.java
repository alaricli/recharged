package com.recharged.backend.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.recharged.backend.dto.CustomerRegistrationRequestDTO;
import com.recharged.backend.entity.Customer;
import com.recharged.backend.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

// Add @SpringBootTest if testing with the full Spring Context
@SpringBootTest
class CustomerServiceTest {

  @Mock
  private CustomerRepository customerRepository;

  @Mock
  private PasswordEncoder passwordEncoder;

  @InjectMocks
  private AuthenticationService customerService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testSignupCustomer() {
    // Arrange
    CustomerRegistrationRequestDTO requestDTO = new CustomerRegistrationRequestDTO();
    requestDTO.setUsername("testuser");
    requestDTO.setEmail("testuser@example.com");
    requestDTO.setPassword("password123");
    requestDTO.setPhoneNumber("1234567890");

    Customer mockCustomer = new Customer();
    mockCustomer.setUsername("testuser");
    mockCustomer.setEmail("testuser@example.com");

    when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
    when(customerRepository.save(any(Customer.class))).thenReturn(mockCustomer);

    // Act
    Customer result = customerService.signupCustomer(requestDTO);

    // Assert
    assertNotNull(result);
    assertEquals("testuser", result.getUsername());
    assertEquals("testuser@example.com", result.getEmail());

    // Verify that dependencies were called as expected
    verify(passwordEncoder).encode("password123");
    verify(customerRepository).save(any(Customer.class));
  }
}