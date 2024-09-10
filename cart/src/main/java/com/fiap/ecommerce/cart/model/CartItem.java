package com.fiap.ecommerce.cart.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.net.ssl.SSLSession;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;
    private int quantity;
    private String productName;
    private Double productPrice;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

}
