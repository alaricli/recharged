package com.recharged.backend.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.recharged.backend.dto.AddToCartRequestDTO;
import com.recharged.backend.dto.CreateCartRequestDTO;
import com.recharged.backend.dto.CartEditRequestDTO;
import com.recharged.backend.entity.Cart;
import com.recharged.backend.entity.CartItem;
import com.recharged.backend.entity.Customer;
import com.recharged.backend.entity.Product;
import com.recharged.backend.repository.CartRepository;
import com.recharged.backend.repository.CustomerRepository;
import com.recharged.backend.repository.ProductRepository;

@Service
public class CartService {
  private final CartRepository cartRepository;
  private final CustomerRepository customerRepository;
  private final ProductRepository productRepository;

  public CartService(CartRepository cartRepository, CustomerRepository customerRepository,
      ProductRepository productRepository) {
    this.cartRepository = cartRepository;
    this.customerRepository = customerRepository;
    this.productRepository = productRepository;
  }

  public void deleteCart(Long cartId) {
    cartRepository.deleteById(cartId);
  }

  public String editCart(Long cartId, CartEditRequestDTO requestDTO) {
    Cart cart = (cartId != null) ? getCart(cartId) : createCart();

    if (cart == null) {
      cart = createCart();
    }

    processCart(cart, requestDTO);

    cartRepository.save(cart);
    return cart.getId().toString();
  }

  private Cart getCart(Long cartId) {
    return cartRepository.findById(cartId).orElse(null);
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
      cartItem.setPrice(requestDTO.getPrice());
      cartItem.setCart(cart);

    } else {
      // if the incoming product is already in the cart, just update the quantity
      // instead
      int updatedQuantity = cartItem.getQuantity() + requestDTO.getQuantity();

      if (updatedQuantity <= 0) {
        cart.getCartItems().remove(cartItem);
      } else {
        cartItem.setQuantity(updatedQuantity);
      }
    }
  }

  private CartItem findCartItem(Cart cart, Long productId) {
    return cart.getCartItems().stream().filter(item -> item.getProduct().getId().equals(productId)).findFirst()
        .orElse(null);
  }
}
