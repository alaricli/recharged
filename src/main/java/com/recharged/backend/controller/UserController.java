package com.recharged.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.recharged.backend.dto.CartItemRequestDTO;
import com.recharged.backend.dto.CartResponseDTO;
import com.recharged.backend.entity.Cart;
import com.recharged.backend.entity.LocalUser;
import com.recharged.backend.service.CartService;
import com.recharged.backend.service.StripeService;
import com.stripe.exception.StripeException;

@RestController
@RequestMapping("/api/user")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class UserController {
  private final CartService cartService;
  private final StripeService stripeService;

  public UserController(CartService cartService, StripeService stripeService) {
    this.cartService = cartService;
    this.stripeService = stripeService;
  }

  // Cart endpoints
  @GetMapping("/cart")
  public ResponseEntity<?> getUserCart(@AuthenticationPrincipal LocalUser user) {
    Cart cart = cartService.getCartByUser(user);
    return ResponseEntity.ok(new CartResponseDTO(cart));
  }

  @PostMapping("/cart/add")
  public ResponseEntity<Void> addItemToUserCart(
      @AuthenticationPrincipal LocalUser user,
      @RequestBody CartItemRequestDTO newCartItemRequestDTO) {
    cartService.addCartItemToUserCart(user, newCartItemRequestDTO);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/cart/edit")
  public ResponseEntity<Void> editItemInUserCart(
      @AuthenticationPrincipal LocalUser user,
      @RequestBody CartItemRequestDTO cartItemRequestDTO) {
    cartService.editItemInUserCart(user, cartItemRequestDTO);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/cart/checkout")
  public ResponseEntity<String> createCheckoutSession(@AuthenticationPrincipal LocalUser user) {
    try {
      String checkoutUrl = stripeService.createStripeCheckoutForUser(user);
      return ResponseEntity.ok(checkoutUrl);
    } catch (StripeException e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("Checkout failed: " + e.getMessage());
    }
  }

  // TODO: Add endpoints for:
  // - GET /orders (view user's orders)
  // - GET /orders/{id} (view specific order)
  // - GET /profile (view user profile)
  // - PUT /profile (update user profile)
}