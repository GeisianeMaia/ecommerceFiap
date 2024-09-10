package com.fiap.ecommerce.payment.dto;

public record CartItemDTO(Long productId, String name, Double price, Integer quantity) {
}
