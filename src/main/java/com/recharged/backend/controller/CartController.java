package com.recharged.backend.controller;

import org.springframework.web.bind.annotation.RestController;

import com.recharged.backend.dto.CartItemRequestDTO;
import com.recharged.backend.dto.CartResponseDTO;
import com.recharged.backend.entity.Cart;
import com.recharged.backend.service.CartService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/cart")
public class CartController {
  private final CartService cartService;

  public CartController(CartService cartService) {
    this.cartService = cartService;
  }

  @GetMapping("/get")
  public ResponseEntity<?> getCartById(
      @CookieValue(value = "cartId", required = false) Long cartId) {
    if (cartId == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
    Cart cart = cartService.getCart(cartId);
    return ResponseEntity.ok(new CartResponseDTO(cart));
  }

  // takes in an optional cookie: cart_id and required cartEditRequest containing:
  // product_id, quantity, and unitPrice
  // if cart_id is present, go straight to adding to cart
  // else, a brand new cart will be made for the user and
  // product will be converted to cart item in the backend
  // cart_item will be attached to the cart
  @PostMapping("/add")
  public ResponseEntity<Void> addItemToCart(
      @CookieValue(value = "cartId", required = false) Long cartId,
      @RequestBody CartItemRequestDTO requestDTO,
      HttpServletResponse response) {

    String updatedCartId = cartService.addCartItemToCart(cartId, requestDTO);

    if (cartId == null) {
      Cookie cartCookie = new Cookie("cartId", updatedCartId);
      cartCookie.setHttpOnly(true);
      cartCookie.setPath("/");
      cartCookie.setMaxAge(7 * 24 * 60 * 60);
      response.addCookie(cartCookie);
    }

    return ResponseEntity.ok().build();
  }

  // editing quantity of a cartItem in a Cart
  @PostMapping("/edit")
  public ResponseEntity<Void> editItemInCart(
      @CookieValue(value = "cartId", required = true) Long cartId,
      @RequestBody CartItemRequestDTO requestDTO,
      HttpServletResponse response) {

    cartService.addCartItemToCart(cartId, requestDTO);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/checkout")
  public ResponseEntity<Void> checkoutCart(
      @CookieValue(value = "cartId", required = false) Long cartId) {
    // Implementation will be added later
    return ResponseEntity.ok().build();
  }
}
