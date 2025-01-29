package com.recharged.backend.controller;

import org.springframework.web.bind.annotation.RestController;

import com.recharged.backend.dto.CartEditRequestDTO;
import com.recharged.backend.service.CartService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController("/cart")
public class CartController {
  private final CartService cartService;

  public CartController(CartService cartService) {
    this.cartService = cartService;
  }

  // takes in an optional cookie: cart_id and cartEditRequest which contains
  // product_id, quantity and price
  // if cart_id, continue
  // if no cart_id, a brand new cart will be made for the user
  // product will be converted to cart item in the backend
  // cart_item will be attached to the cart
  @PostMapping("/edit")
  public ResponseEntity<Void> editCart(
      @CookieValue(value = "cartId", required = false) Long cartId,
      @RequestBody CartEditRequestDTO requestDTO,
      HttpServletResponse response) {

    String updatedCartId = cartService.editCart(cartId, requestDTO);

    if (cartId == null) {
      Cookie cartCookie = new Cookie("cartId", updatedCartId);
      cartCookie.setHttpOnly(true);
      cartCookie.setPath("/");
      cartCookie.setMaxAge(7 * 24 * 60 * 60);
      response.addCookie(cartCookie);
    }

    return ResponseEntity.ok().build();
  }
}
