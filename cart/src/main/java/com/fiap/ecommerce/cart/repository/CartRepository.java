package com.fiap.ecommerce.cart.repository;

import com.fiap.ecommerce.cart.model.Cart;
import com.fiap.ecommerce.cart.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

}
