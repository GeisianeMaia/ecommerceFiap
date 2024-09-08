package com.fiap.ecommerce.cart.service.impl;

import com.fiap.ecommerce.cart.dto.CartDTO;
import com.fiap.ecommerce.cart.dto.CartItemDTO;
import com.fiap.ecommerce.cart.model.Cart;
import com.fiap.ecommerce.cart.model.CartItem;
import com.fiap.ecommerce.cart.repository.CartRepository;
import com.fiap.ecommerce.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Override
    public CartDTO createCart(CartDTO cartDTO) {
        // Cria um novo carrinho com uma lista de itens vazia
        Cart cart = new Cart();
        cart.setItems(new ArrayList<>()); // Inicialize com uma lista vazia

        // Salva o carrinho no banco de dados
        cartRepository.save(cart);

        // Cria o CartDTO com uma lista de itens vazia, já que o carrinho está vazio no início
        return new CartDTO(cart.getId(), new ArrayList<>());
    }

    @Override
    public CartDTO addItem(Long cartId, CartItemDTO cartItemDTO) {
        Optional<Cart> cartOpt = cartRepository.findById(cartId);
        if (cartOpt.isPresent()) {
            Cart cart = cartOpt.get();
            CartItem cartItem = new CartItem();
            cartItem.setProductId(cartItemDTO.productId());
            cartItem.setQuantity(cartItemDTO.quantity());
            cartItem.setCart(cart);
            cart.getItems().add(cartItem); // Adiciona o item ao carrinho
            cartRepository.save(cart); // Salva o carrinho e seus itens
            return new CartDTO(cart.getId(), cart.getItems().stream()
                    .map(item -> new CartItemDTO(item.getProductId(), item.getQuantity()))
                    .collect(Collectors.toList()));
        }
        throw new RuntimeException("Cart not found");
    }

    @Override
    public CartDTO getCartById(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        List<CartItemDTO> cartItemDTOs = cart.getItems().stream()
                .map(item -> new CartItemDTO(item.getProductId(), item.getQuantity()))
                .collect(Collectors.toList());
        return new CartDTO(cart.getId(), cartItemDTOs);
    }
    @Override
    public CartDTO removeItem(Long cartId, Long itemId) {
        Optional<Cart> cartOpt = cartRepository.findById(cartId);
        if (cartOpt.isPresent()) {
            Cart cart = cartOpt.get();

            // Remove o item com o ID correspondente
            cart.getItems().removeIf(item -> item.getId().equals(itemId));

            // Salva o carrinho atualizado no banco de dados
            cartRepository.save(cart);

            // Converte os itens restantes para CartItemDTO
            List<CartItemDTO> cartItemDTOs = cart.getItems().stream()
                    .map(item -> new CartItemDTO(item.getProductId(), item.getQuantity()))
                    .collect(Collectors.toList());

            // Retorna o CartDTO com a lista de CartItemDTOs atualizada
            return new CartDTO(cart.getId(), cartItemDTOs);
        }
        throw new RuntimeException("Cart not found");
    }
}
