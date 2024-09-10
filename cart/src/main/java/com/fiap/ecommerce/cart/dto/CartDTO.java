package com.fiap.ecommerce.cart.dto;

import java.util.List;

public record CartDTO(Long id,  Long userId, List<CartItemDTO> items) {
}
