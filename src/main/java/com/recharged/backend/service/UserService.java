package com.recharged.backend.service;

import java.util.List;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;

import com.recharged.backend.dto.UserAuthenticationRequestDTO;
import com.recharged.backend.dto.UserRegistrationRequestDTO;
import com.recharged.backend.entity.LocalUser;
import com.recharged.backend.repository.UserRepository;

@Service
public class UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public UserService(
      UserRepository userRepository,
      PasswordEncoder passwordEncoder,
      JwtService jwtService,
      AuthenticationManager authenticationManager) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtService = jwtService;
    this.authenticationManager = authenticationManager;
  }

  public LocalUser signupUser(UserRegistrationRequestDTO newUserDetails) {
    LocalUser user = new LocalUser();
    user.setUsername(newUserDetails.getUsername());
    user.setEmail(newUserDetails.getEmail());
    user.setFirstName(newUserDetails.getFirstName());
    user.setLastName(newUserDetails.getLastName());
    user.setPassword(passwordEncoder.encode(newUserDetails.getPassword()));
    user.setPhoneNumber(newUserDetails.getPhoneNumber());
    user.setRole("ROLE_USER");
    return userRepository.save(user);
  }

  public LocalUser loginUser(UserAuthenticationRequestDTO userCredentials) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(userCredentials.getUsername(), userCredentials.getPassword()));
    return userRepository.findByUsername(userCredentials.getUsername()).orElseThrow();
  }

  public List<LocalUser> findAllUsers() {
    return userRepository.findAll();
  }

  public LocalUser findById(Long id) {
    return userRepository.findById(id).orElseThrow();
  }

  public LocalUser findByUsername(String username) {
    return userRepository.findByUsername(username).orElseThrow();
  }

  public LocalUser getUserInfo(String token) {
    String username = jwtService.extractUsername(token);
    return findByUsername(username);
  }

  /**
   * Creates an admin user for testing purposes
   */
  @PostConstruct
  public void createTestAdmin() {
    // Check if admin already exists
    if (userRepository.findByUsername("admin").isPresent()) {
      return; // Admin already exists, nothing to do
    }

    // Create admin user
    LocalUser admin = new LocalUser();
    admin.setUsername("admin");
    admin.setEmail("admin@example.com");
    admin.setFirstName("Admin");
    admin.setLastName("User");
    admin.setPassword(passwordEncoder.encode("admin123")); // Simple password for testing
    admin.setPhoneNumber("1234567890");
    admin.setRole("ROLE_ADMIN");

    userRepository.save(admin);
    System.out.println("Created admin user for testing");
  }

  /**
   * Save an existing user (for updates)
   */
  public LocalUser saveUser(LocalUser user) {
    return userRepository.save(user);
  }
}
