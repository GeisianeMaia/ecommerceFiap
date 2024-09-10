package com.fiap.ecommerce.cart.dto;

public record CartItemDTO(Long productId, int quantity, String productName, Double productPrice) {
}
