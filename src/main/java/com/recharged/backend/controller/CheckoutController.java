package com.recharged.backend.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/checkout")
public class CheckoutController {
  // create order upon user clicking checkout with a cart
  // submit the order too
  @PostMapping("/{cartId}")
  public String postMethodName(@RequestBody String entity) {
    // TODO: process POST request

    return entity;
  }
}
