package com.fiap.ecommerce.cart.dto;

public record ItemDTO(Long id, String nameItem, String description, double price, boolean hasStock) {
}

