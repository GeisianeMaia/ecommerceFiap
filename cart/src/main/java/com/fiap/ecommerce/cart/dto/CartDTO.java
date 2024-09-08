package com.fiap.ecommerce.cart.dto;

import java.util.List;

public record CartDTO(Long id, List<CartItemDTO> items) {
}
