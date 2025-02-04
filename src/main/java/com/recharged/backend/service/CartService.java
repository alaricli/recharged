package com.recharged.backend.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.recharged.backend.dto.CartItemRequestDTO;
import com.recharged.backend.entity.Cart;
import com.recharged.backend.entity.CartItem;
import com.recharged.backend.repository.CartItemRepository;
import com.recharged.backend.repository.CartRepository;
import com.recharged.backend.repository.ProductRepository;

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

  // logic for adding a new item to a cart that either exist or not
  public String addCartItemToCart(Long cartId, CartItemRequestDTO requestDTO) {
    // check for existing cart, if there's no existing cart, make one
    Cart cart = (cartId != null) ? getCart(cartId) : createNewCart();

    CartItem existingCartItem = findCartItem(cart, requestDTO.getSku());

    if (existingCartItem != null) {
      existingCartItem.setQuantity(existingCartItem.getQuantity() + requestDTO.getQuantity());
    } else {
      CartItem newCartItem = createCartItem(requestDTO);
      newCartItem.setCart(cart);
      cart.getCartItems().add(newCartItem);
    }

    cartRepository.save(cart);
    return cart.getId().toString();
  }

  // logic for editing the quantity of a cartitem in a cart
  public void editItemInCart(Long cartId, CartItemRequestDTO requestDTO) {
    Cart cart = getCart(cartId);
    // pull the cartItem from the cart according to the requestDTO
    // it should be there, otherwise throw error
    CartItem cartItemToEdit = findCartItem(cart, requestDTO.getSku());
    if (cartItemToEdit == null) {
      throw new IllegalArgumentException("Cart Item should already be in cart");
    }

    int updatedQuantity = cartItemToEdit.getQuantity() + requestDTO.getQuantity();
    // remove the cartItem from the cart if the new quantity is going to be 0,
    // otherwise simply update the quantity
    if (updatedQuantity <= 0) {
      cart.getCartItems().remove(cartItemToEdit);
      cartItemRepository.delete(cartItemToEdit);
    } else {
      cartItemToEdit.setQuantity(updatedQuantity);
    }

    // delete the entire cart if the cart now has nothing, otherwise save the
    // updated cart
    if (cart.getCartItems().size() == 0) {
      deleteCart(cartId);
    } else {
      cartRepository.save(cart);
    }
  }

  // HELPERS
  public Cart getCart(Long cartId) {
    return cartRepository.findById(cartId).orElse(null);
  }

  public void deleteCart(Long cartId) {
    cartRepository.deleteById(cartId);
  }

  private Cart createNewCart() {
    Cart newCart = new Cart();
    newCart.setCartItems(new ArrayList<>());
    return newCart;
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
}
