package com.recharged.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.recharged.backend.dto.ProductRequestDTO;
import com.recharged.backend.entity.Cart;
import com.recharged.backend.entity.LocalUser;
import com.recharged.backend.entity.Product;
import com.recharged.backend.entity.PurchaseOrder;
import com.recharged.backend.service.CartService;
import com.recharged.backend.service.OrderService;
import com.recharged.backend.service.ProductService;
import com.recharged.backend.service.UserService;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
  private final UserService userService;
  private final OrderService orderService;
  private final CartService cartService;
  private final ProductService productService;

  public AdminController(
      UserService userService,
      OrderService orderService,
      CartService cartService,
      ProductService productService) {
    this.userService = userService;
    this.orderService = orderService;
    this.cartService = cartService;
    this.productService = productService;
  }

  // ==================== User Management ====================
  @GetMapping("/users")
  public ResponseEntity<List<LocalUser>> getAllUsers() {
    return ResponseEntity.ok(userService.findAllUsers());
  }

  @GetMapping("/users/{id}")
  public ResponseEntity<LocalUser> getUser(@PathVariable Long id) {
    return ResponseEntity.ok(userService.findById(id));
  }

  // ==================== Order Management ====================
  @GetMapping("/orders")
  public ResponseEntity<List<PurchaseOrder>> getAllOrders() {
    return ResponseEntity.ok(orderService.findAllOrders());
  }

  @GetMapping("/orders/{id}")
  public ResponseEntity<PurchaseOrder> getOrder(@PathVariable Long id) {
    return ResponseEntity.ok(orderService.findById(id));
  }

  // ==================== Cart Management ====================
  @GetMapping("/carts")
  public ResponseEntity<List<Cart>> getAllCarts() {
    return ResponseEntity.ok(cartService.getAllCarts());
  }

  @GetMapping("/carts/{id}")
  public ResponseEntity<Cart> getCart(@PathVariable Long id) {
    return ResponseEntity.ok(cartService.getCartById(id));
  }

  // ==================== Inventory Management ====================
  /**
   * Add new products to the system
   * Only accessible by admin users
   */
  @PostMapping("/products")
  public ResponseEntity<List<Product>> addProducts(@RequestBody List<ProductRequestDTO> productRequests) {
    List<Product> createdProducts = productService.addProducts(productRequests);
    return ResponseEntity.ok(createdProducts);
  }

  // TODO: Add endpoints for:
  // User Management:
  // - PUT /users/{id} (update user)
  // - DELETE /users/{id} (delete user)

  // Order Management:
  // - PUT /orders/{id} (update order status)

  // Product Management:
  // - DELETE /products/{id} (delete product)
  // - PUT /products/{id} (update product)
}