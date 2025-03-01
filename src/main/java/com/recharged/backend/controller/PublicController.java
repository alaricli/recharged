package com.recharged.backend.controller;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.recharged.backend.dto.ProductRequestDTO;
// DTOs
import com.recharged.backend.dto.ProductResponseDTO;
import com.recharged.backend.dto.SimpleProductResponseDTO;
import com.recharged.backend.dto.UserAuthenticationRequestDTO;
import com.recharged.backend.dto.UserRegistrationRequestDTO;

// Entities
import com.recharged.backend.entity.LocalUser;
import com.recharged.backend.entity.Product;

// Services
import com.recharged.backend.service.JwtService;
import com.recharged.backend.service.ProductService;
import com.recharged.backend.service.UserService;

@RestController
@RequestMapping("/api/public")
public class PublicController {
  private final ProductService productService;
  private final UserService userService;
  private final JwtService jwtService;

  public PublicController(ProductService productService,
      UserService userService,
      JwtService jwtService) {
    this.productService = productService;
    this.userService = userService;
    this.jwtService = jwtService;
  }

  // Product endpoints
  @GetMapping("/get/products")
  public ResponseEntity<List<Product>> getAllProducts() {
    List<Product> products = productService.findAll();
    return ResponseEntity.ok(products);
  }

  @GetMapping("/get/products/sku/{sku}")
  public ResponseEntity<ProductResponseDTO> getProduct(@PathVariable String sku) {
    ProductResponseDTO product = productService.findBySku(sku);
    return ResponseEntity.ok(product);
  }

  @GetMapping("/get/products/category/{category}")
  public ResponseEntity<List<SimpleProductResponseDTO>> getProductsByCategory(@PathVariable String category) {
    List<SimpleProductResponseDTO> products = productService.findAllByCategory(category);
    return ResponseEntity.ok(products);
  }

  @GetMapping("/get/products/paginated/category/{category}")
  public ResponseEntity<Page<SimpleProductResponseDTO>> getProductsByCategoryPaginated(
      @PathVariable String category,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "2") int size) {

    Page<SimpleProductResponseDTO> products = productService.findAllByCategoryPaginated(category, page, size);
    return ResponseEntity.ok(products);
  }

  // User endpoints
  @PostMapping("/signup")
  public ResponseEntity<?> userSignUp(@RequestBody UserRegistrationRequestDTO newUserDetails) {
    LocalUser newUser = userService.signupUser(newUserDetails);
    return ResponseEntity.ok().body("successfully registered:" + newUser);
  }

  @PostMapping("/login")
  public ResponseEntity<?> userLogIn(@RequestBody UserAuthenticationRequestDTO credentials) {
    LocalUser authenticatedUser = userService.loginUser(credentials);
    String jwtToken = jwtService.generateToken(authenticatedUser);
    return ResponseEntity.ok().body(jwtToken);
  }

  // Temporary endpoints
  @PostMapping("/add/products")
  public ResponseEntity<List<Product>> addProductTest(@RequestBody List<ProductRequestDTO> newProducts) {
    return ResponseEntity.ok(productService.addProducts(newProducts));
  }

  /**
   * Simple endpoint that generates a long-lived admin token for testing
   * Requires a header "X-API-KEY" with the correct secret value
   */
  @GetMapping("/dev")
  public ResponseEntity<String> getTestAdminToken(@RequestHeader(name = "X-API-KEY", required = false) String apiKey) {
    // Check for API key
    if (apiKey == null || !apiKey.equals("Gtafive5")) {
      return ResponseEntity.status(403).body("Access denied: Invalid or missing API key");
    }

    try {
      // Check if admin exists, if not, create one
      LocalUser adminUser;
      try {
        adminUser = userService.findByUsername("admin");
      } catch (Exception e) {
        // Admin doesn't exist, let's create one
        UserRegistrationRequestDTO adminDTO = new UserRegistrationRequestDTO();
        adminDTO.setUsername("admin");
        adminDTO.setEmail("alaricli@outlook.com");
        adminDTO.setFirstName("Alaric");
        adminDTO.setLastName("Li");
        adminDTO.setPassword("Gtafive5");
        adminDTO.setPhoneNumber("7789863368");

        adminUser = userService.signupUser(adminDTO);
        adminUser.setRole("ROLE_ADMIN");
        adminUser = userService.saveUser(adminUser); // Save the updated role
        System.out.println("Created new admin user for token generation");
      }

      // Generate a long-lived token
      String token = jwtService.generateLongLivedAdminToken(adminUser);
      return ResponseEntity.ok(token);
    } catch (Exception e) {
      return ResponseEntity.status(500).body("Error generating token: " + e.getMessage());
    }
  }
}