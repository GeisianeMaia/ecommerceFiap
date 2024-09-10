package com.fiap.ecommerce.cart.service;

import com.fiap.ecommerce.cart.dto.CartDTO;
import com.fiap.ecommerce.cart.dto.CartItemDTO;

public interface CartService {
    CartDTO createCart(CartDTO cartDTO);
    CartDTO addItem(Long cartId, CartItemDTO cartItemDTO, String token);
    CartDTO getCartById(Long cartId);
    CartDTO removeItem(Long cartId, Long itemId);
}
