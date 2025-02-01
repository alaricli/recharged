package com.recharged.backend.service;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.recharged.backend.dto.CartEditRequestDTO;
import com.recharged.backend.entity.Cart;
import com.recharged.backend.entity.CartItem;
import com.recharged.backend.repository.CartItemRepository;
import com.recharged.backend.repository.CartRepository;
import com.recharged.backend.repository.ProductRepository;

@Service
public class CartService {
  private final CartRepository cartRepository;
  private final ProductRepository productRepository;
  private final CartItemRepository cartItemRepository;

  public CartService(
      CartRepository cartRepository,
      ProductRepository productRepository,
      CartItemRepository cartItemRepository) {
    this.cartRepository = cartRepository;
    this.productRepository = productRepository;
    this.cartItemRepository = cartItemRepository;
  }

  // logic for adding a new item to a cart that either exist or not
  public String addToCart(Long cartId, CartEditRequestDTO requestDTO) {
    Cart cart = (cartId != null) ? getCart(cartId) : createCart();

    if (cart == null) {
      cart = createCart();
    }

    processCart(cart, requestDTO);
    return cart.getId().toString();
  }

  // logic for editing the quantity of a cartitem in a cart
  public void editCart(Long cartId, CartEditRequestDTO requestDTO) {
    if (cartId == null) {
      throw new IllegalArgumentException("Cart ID cannot be null");
    }
    Cart cart = getCart(cartId);
    processCart(cart, requestDTO);
    if (cart.getCartItems().size() == 0) {
      deleteCart(cartId);
    }
  }

  // HELPERS
  public Cart getCart(Long cartId) {
    return cartRepository.findById(cartId).orElse(null);
  }

  public void deleteCart(Long cartId) {
    cartRepository.deleteById(cartId);
  }

  private Cart createCart() {
    Cart newCart = new Cart();
    newCart.setCartItems(new ArrayList<>());
    return cartRepository.save(newCart);
  }

  private void processCart(Cart cart, CartEditRequestDTO requestDTO) {
    CartItem cartItem = findCartItem(cart, requestDTO.getProductId());

    // if the product isn't in the cart yet, create the cartitem
    // and add it to the cart
    if (cartItem == null) {
      cartItem = new CartItem();
      cartItem.setProduct(productRepository.findById(requestDTO.getProductId()).orElseThrow(
          () -> new RuntimeException("Product not found")));
      cartItem.setQuantity(requestDTO.getQuantity());

      BigDecimal price = requestDTO.getPrice() != null ? requestDTO.getPrice() : BigDecimal.ZERO;
      cartItem.setPrice(price);

      cart.addCartItem(cartItem);
    } else {
      // if the incoming product is already in the cart, just update the quantity
      // instead
      int updatedQuantity = cartItem.getQuantity() + requestDTO.getQuantity();

      if (updatedQuantity <= 0) {
        cartItemRepository.delete(cartItem);
        cart.getCartItems().remove(cartItem);
      } else {
        cartItem.setQuantity(updatedQuantity);
      }
    }

    cartRepository.save(cart);
  }

  private CartItem findCartItem(Cart cart, Long productId) {
    return cart.getCartItems().stream().filter(item -> item.getProduct().getId().equals(productId)).findFirst()
        .orElse(null);
  }
}
