package com.fiap.ecommerce.payment.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.ecommerce.payment.dto.CartDTO;
import com.fiap.ecommerce.payment.dto.CartItemDTO;
import com.fiap.ecommerce.payment.model.Payment;
import com.fiap.ecommerce.payment.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final RestTemplate restTemplate;

    @Value("${cart.service.url}")
    private String cartServiceUrl;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public PaymentService(PaymentRepository paymentRepository, RestTemplate restTemplate) {
        this.paymentRepository = paymentRepository;
        this.restTemplate = restTemplate;
    }

    public Payment createPayment(Long userId, String pixKey) throws IOException {
        CartDTO cart = getMockedCartByUserId(userId);

        String cartJson = objectMapper.writeValueAsString(cart);

        Payment payment = new Payment();
        payment.setPixKey(pixKey);
        payment.setAmount(cart.getTotalAmount());
        payment.setStatus("PENDING");
        payment.setUserId(userId);
        payment.setCart(cartJson);

        return paymentRepository.save(payment);
    }

    public Payment completePayment(Long id) {
        Payment payment = paymentRepository.findById(id).orElseThrow();
        payment.setStatus("COMPLETED");
        return paymentRepository.save(payment);
    }

    private CartDTO getMockedCartByUserId(Long userId) {
        List<CartItemDTO> items = List.of(
                new CartItemDTO(1L, "Produto A", 50.0, 2),
                new CartItemDTO(2L, "Produto B", 30.0, 1)
        );
        return new CartDTO(userId, items);
    }
}