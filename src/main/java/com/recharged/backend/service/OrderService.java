package com.recharged.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.recharged.backend.entity.Cart;
import com.recharged.backend.entity.CartItem;
import com.recharged.backend.entity.LocalUser;
import com.recharged.backend.entity.OrderItem;
import com.recharged.backend.entity.PurchaseOrder;
import com.recharged.backend.repository.OrderRepository;

@Service
public class OrderService {
  private final OrderRepository orderRepository;

  public OrderService(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  public List<PurchaseOrder> findAllOrders() {
    return orderRepository.findAll();
  }

  public PurchaseOrder findById(Long id) {
    return orderRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
  }

  public PurchaseOrder findByStripeSessionId(String sessionId) {
    return orderRepository.findByStripeSessionId(sessionId)
        .orElseThrow(() -> new RuntimeException("Order not found with session id: " + sessionId));
  }

  @Transactional
  public PurchaseOrder createOrderFromCart(Cart cart, String stripeSessionId) {
    PurchaseOrder order = new PurchaseOrder();
    order.setUser(cart.getUser());
    order.setStripeSessionId(stripeSessionId);
    order.setOrderStatus("created");

    // Convert cart items to order items
    List<OrderItem> orderItems = cart.getCartItems().stream()
        .map(this::convertCartItemToOrderItem)
        .collect(Collectors.toList());

    orderItems.forEach(item -> item.setOrder(order));
    order.setOrderItems(orderItems);

    return orderRepository.save(order);
  }

  @Transactional
  public PurchaseOrder updateOrderStatus(String stripeSessionId, String newStatus) {
    PurchaseOrder order = findByStripeSessionId(stripeSessionId);
    order.setOrderStatus(newStatus);
    return orderRepository.save(order);
  }

  public List<PurchaseOrder> findOrdersByUser(LocalUser user) {
    return orderRepository.findAll().stream()
        .filter(order -> order.getUser().equals(user))
        .collect(Collectors.toList());
  }

  // Helper methods
  private OrderItem convertCartItemToOrderItem(CartItem cartItem) {
    OrderItem orderItem = new OrderItem();
    orderItem.setProduct(cartItem.getProduct());
    orderItem.setQuantity(cartItem.getQuantity().intValue());
    return orderItem;
  }
}