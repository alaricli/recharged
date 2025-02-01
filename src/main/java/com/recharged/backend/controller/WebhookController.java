package com.recharged.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/webhooks")
public class WebhookController {

  @PostMapping("/stripe")
  public String handleStripeWebhook(@RequestBody String entity) {
    // TODO: process POST request sent by Stripe after a checkout
    // update order status
    // send customer notification
    // send admin notification

    return entity;
  }

}
