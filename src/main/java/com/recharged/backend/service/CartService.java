package com.recharged.backend.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.recharged.backend.dto.CartItemRequestDTO;
import com.recharged.backend.entity.Cart;
import com.recharged.backend.entity.CartItem;
import com.recharged.backend.repository.CartItemRepository;
import com.recharged.backend.repository.CartRepository;
import com.recharged.backend.repository.ProductRepository;
import com.recharged.backend.entity.LocalUser;

@Service
public class CartService {
  private final CartRepository cartRepository;
  private final CartItemRepository cartItemRepository;
  private final ProductRepository productRepository;

  public CartService(
      CartRepository cartRepository,
      CartItemRepository cartItemRepository,
      ProductRepository productRepository) {
    this.cartRepository = cartRepository;
    this.cartItemRepository = cartItemRepository;
    this.productRepository = productRepository;
  }

  public void addCartItemToUserCart(LocalUser user, CartItemRequestDTO requestDTO) {
    Cart cart = getCartByUser(user);
    CartItem existingCartItem = findCartItem(cart, requestDTO.getSku());

    if (existingCartItem != null) {
      existingCartItem.setQuantity(existingCartItem.getQuantity() + requestDTO.getQuantity());
    } else {
      CartItem newCartItem = createCartItem(requestDTO);
      newCartItem.setCart(cart);
      cart.getCartItems().add(newCartItem);
    }

    cart.setLastUpdatedDateTime(LocalDateTime.now());
    cartRepository.save(cart);
  }

  public void editItemInUserCart(LocalUser user, CartItemRequestDTO requestDTO) {
    Cart cart = getCartByUser(user);
    CartItem cartItemToEdit = findCartItem(cart, requestDTO.getSku());
    if (cartItemToEdit == null) {
      throw new IllegalArgumentException("Cart Item not found in user's cart");
    }

    if (requestDTO.getQuantity() <= 0) {
      cart.getCartItems().remove(cartItemToEdit);
      cartItemRepository.delete(cartItemToEdit);
    } else {
      cartItemToEdit.setQuantity(requestDTO.getQuantity());
    }

    cart.setLastUpdatedDateTime(LocalDateTime.now());
    cartRepository.save(cart);
  }

  public List<Cart> getAllCarts() {
    return cartRepository.findAll();
  }

  public Cart getCartById(Long cartId) {
    return cartRepository.findById(cartId).orElse(null);
  }

  public Cart getCart(Long cartId) {
    return cartRepository.findById(cartId).orElse(null);
  }

  public void deleteCart(Long cartId) {
    cartRepository.deleteById(cartId);
  }

  private CartItem findCartItem(Cart cart, String productSku) {
    return cart.getCartItems().stream().filter(item -> item.getProduct().getSku().equals(productSku)).findFirst()
        .orElse(null);
  }

  private CartItem createCartItem(CartItemRequestDTO requestDTO) {
    CartItem newCartItem = new CartItem();

    newCartItem.setProduct(productRepository.findBySku(requestDTO.getSku()).orElseThrow(
        () -> new RuntimeException("Product not found")));
    newCartItem.setQuantity(requestDTO.getQuantity());

    return newCartItem;
  }

  public Cart getCartByUser(LocalUser user) {
    Cart cart = cartRepository.findByUser(user);
    if (cart == null) {
      cart = createNewCart();
      cart.setUser(user);
      cart = cartRepository.save(cart);
      user.setCart(cart);
    }
    return cart;
  }

  private Cart createNewCart() {
    Cart newCart = new Cart();
    newCart.setCartItems(new ArrayList<>());
    return newCart;
  }

}
