package com.fiap.ecommerce.payment.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.ecommerce.payment.dto.CartDTO;
import com.fiap.ecommerce.payment.dto.CartItemDTO;
import com.fiap.ecommerce.payment.model.Payment;
import com.fiap.ecommerce.payment.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class PaymentServiceTest {

    @InjectMocks
    private PaymentService paymentService;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private RestTemplate restTemplate;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testCreatePayment() throws IOException {
        Long userId = 1L;
        Payment mockPayment = new Payment();
        mockPayment.setId(1L);
        mockPayment.setUserId(userId);
        mockPayment.setPixKey("generated-pix-key");

        when(paymentRepository.save(mockPayment)).thenReturn(mockPayment);

        Payment createdPayment = paymentService.createPayment(userId, "generated-pix-key");
        assertNotNull(createdPayment);
    }

    @Test
    public void testCompletePayment() {
        Long paymentId = 1L;
        Payment mockPayment = new Payment();
        mockPayment.setId(paymentId);
        mockPayment.setStatus("PENDING");

        when(paymentRepository.findById(paymentId)).thenReturn(java.util.Optional.of(mockPayment));
        when(paymentRepository.save(mockPayment)).thenReturn(mockPayment);

        Payment completedPayment = paymentService.completePayment(paymentId);
        assertNotNull(completedPayment);
    }
}
