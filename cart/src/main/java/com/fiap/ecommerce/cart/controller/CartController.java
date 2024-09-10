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
    public ResponseEntity<CartDTO> createCart(@RequestHeader("Authorization") String authorizationHeader, @RequestBody CartDTO cartDTO) {
        Long userId = getUserIdFromToken(authorizationHeader);
        CartDTO updatedCartDTO = new CartDTO(cartDTO.id(), userId, cartDTO.items());
        CartDTO createdCart = cartService.createCart(updatedCartDTO);
        System.out.println("createdCart"+createdCart);
        return new ResponseEntity<>(createdCart, HttpStatus.CREATED);
    }

    @PostMapping("/{cartId}/items")
    public ResponseEntity<CartDTO> addItemToCart(@PathVariable Long cartId, @RequestHeader("Authorization") String authorizationHeader, @RequestBody CartItemDTO cartItemDTO) {
        String token = getTokenFromHeader(authorizationHeader);
        System.out.println("token"+token);
        CartDTO updatedCart = cartService.addItem(cartId, cartItemDTO, token);
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

    private String getTokenFromHeader(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        throw new IllegalArgumentException("Invalid Authorization header format");
    }

    private Long getUserIdFromToken(String authorizationHeader) {
        String token = getTokenFromHeader(authorizationHeader);
        return 123L;
    }
}
