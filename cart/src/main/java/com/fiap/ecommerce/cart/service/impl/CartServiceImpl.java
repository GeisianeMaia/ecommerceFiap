package com.fiap.ecommerce.cart.service.impl;

import com.fiap.ecommerce.cart.dto.CartDTO;
import com.fiap.ecommerce.cart.dto.CartItemDTO;
import com.fiap.ecommerce.cart.dto.ItemDTO;
import com.fiap.ecommerce.cart.model.Cart;
import com.fiap.ecommerce.cart.model.CartItem;
import com.fiap.ecommerce.cart.repository.CartRepository;
import com.fiap.ecommerce.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private RestTemplate restTemplate;

    private final String productServiceUrl = "http://localhost:8082/item/";

    @Override
    public CartDTO createCart(CartDTO cartDTO) {
        Cart cart = new Cart();
        cart.setItems(new ArrayList<>());
        cart.setUserId(cartDTO.userId());

        cartRepository.save(cart);

        return new CartDTO(cart.getId(), cart.getUserId(), cart.getItems().stream()
                .map(item -> new CartItemDTO(item.getProductId(), item.getQuantity(), item.getProductName(), item.getProductPrice()))
                .collect(Collectors.toList()));
    }


    @Override
    public CartDTO addItem(Long cartId, CartItemDTO cartItemDTO, String token) {
        Optional<Cart> cartOpt = cartRepository.findById(cartId);
        if (cartOpt.isPresent()) {
            Cart cart = cartOpt.get();

            Optional<CartItem> existingItemOpt = cart.getItems().stream()
                    .filter(item -> item.getProductId().equals(cartItemDTO.productId()))
                    .findFirst();

            if (existingItemOpt.isPresent()) {
                CartItem existingItem = existingItemOpt.get();
                existingItem.setQuantity(existingItem.getQuantity() + cartItemDTO.quantity());
            } else {
                CartItem newItem = new CartItem();
                newItem.setProductId(cartItemDTO.productId());
                newItem.setQuantity(cartItemDTO.quantity());

                ItemDTO product = restTemplate.getForObject(productServiceUrl + cartItemDTO.productId(), ItemDTO.class);
                if (product != null) {
                    newItem.setProductName(product.nameItem());
                    newItem.setProductPrice(product.price());
                }

                newItem.setCart(cart);
                cart.getItems().add(newItem);
            }

            cartRepository.save(cart);

            return new CartDTO(cart.getId(), cart.getUserId(), cart.getItems().stream()
                    .map(item -> new CartItemDTO(item.getProductId(), item.getQuantity(), item.getProductName(), item.getProductPrice()))
                    .collect(Collectors.toList()));
        }
        throw new RuntimeException("Cart not found");
    }



    @Override
    public CartDTO getCartById(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        List<CartItemDTO> cartItemDTOs = cart.getItems().stream()
                .map(item -> new CartItemDTO(item.getProductId(), item.getQuantity(), item.getProductName(), item.getProductPrice()))
                .collect(Collectors.toList());
        return new CartDTO(cart.getId(),cart.getUserId(), cartItemDTOs);
    }
    @Override
    public CartDTO removeItem(Long cartId, Long productId) {
        Optional<Cart> cartOpt = cartRepository.findById(cartId);
        if (cartOpt.isPresent()) {
            Cart cart = cartOpt.get();

            // Log para verificar os itens antes da remoção
            System.out.println("Items in the cart before removal:");
            cart.getItems().forEach(item -> System.out.println("Product ID: " + item.getProductId()));

            boolean removed = cart.getItems().removeIf(item -> {
                boolean match = item.getProductId().equals(productId);
                System.out.println("Comparing productId: " + item.getProductId() + " with " + productId + " -> match: " + match);
                return match;
            });

            System.out.println("Item removed: " + removed);

            // Log para verificar os itens após a remoção
            System.out.println("Items in the cart after removal:");
            cart.getItems().forEach(item -> System.out.println("Product ID: " + item.getProductId()));

            cartRepository.save(cart);

            return new CartDTO(cart.getId(), cart.getUserId(), cart.getItems().stream()
                    .map(item -> new CartItemDTO(item.getProductId(), item.getQuantity(), item.getProductName(), item.getProductPrice()))
                    .collect(Collectors.toList()));
        }
        throw new RuntimeException("Cart not found");
    }


}
