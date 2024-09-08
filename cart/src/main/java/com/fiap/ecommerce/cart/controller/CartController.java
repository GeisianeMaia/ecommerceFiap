package com.fiap.ecommerce.cart.controller;

import com.fiap.ecommerce.cart.dto.CartDTO;
import com.fiap.ecommerce.cart.dto.CartItemDTO;
import com.fiap.ecommerce.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @PostMapping
    public ResponseEntity<CartDTO> createCart(@RequestBody CartDTO cartDTO) {
        CartDTO createdCart = cartService.createCart(cartDTO);
        return new ResponseEntity<>(createdCart, HttpStatus.CREATED);
    }

    @PostMapping("/{cartId}/items")
    public ResponseEntity<CartDTO> addItemToCart(@PathVariable Long cartId, @RequestBody CartItemDTO cartItemDTO) {
        CartDTO updatedCart = cartService.addItem(cartId, cartItemDTO);
        return new ResponseEntity<>(updatedCart, HttpStatus.OK);
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<CartDTO> getCartById(@PathVariable Long cartId) {
        CartDTO cartDTO = cartService.getCartById(cartId);
        return new ResponseEntity<>(cartDTO, HttpStatus.OK);
    }
    @DeleteMapping("/{cartId}/remove/{itemId}")
    public ResponseEntity<CartDTO> removeItem(@PathVariable Long cartId, @PathVariable Long itemId) {
        CartDTO cartDTO = cartService.removeItem(cartId, itemId);
        return ResponseEntity.ok(cartDTO);
    }
}
