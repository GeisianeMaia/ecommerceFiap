package com.fiap.ecommerce.payment.dto;

import java.util.List;

public record CartDTO(Long userId, List<CartItemDTO> items) {
    public Double getTotalAmount() {
        return items.stream().mapToDouble(item -> item.price() * item.quantity()).sum();
    }
}
