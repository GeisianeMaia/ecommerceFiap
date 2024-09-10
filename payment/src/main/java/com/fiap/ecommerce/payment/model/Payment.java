package com.fiap.ecommerce.payment.model;

import com.fiap.ecommerce.payment.dto.CartDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String pixKey;
    private Double amount;
    private String status;
    private Long userId;
    private String cart;
}
