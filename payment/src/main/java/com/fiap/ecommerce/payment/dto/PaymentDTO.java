package com.fiap.ecommerce.payment.dto;

public record PaymentDTO( Long id,String pixKey,Double amount,String status, Long userId,String cart) {
}
