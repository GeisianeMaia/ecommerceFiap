package com.fiap.ecommerce.payment.controller;

import com.fiap.ecommerce.payment.model.Payment;
import com.fiap.ecommerce.payment.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class PaymentControllerTest {

    @InjectMocks
    private PaymentController paymentController;

    @Mock
    private PaymentService paymentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreatePayment() throws Exception {
        Long userId = 1L;
        Payment mockPayment = new Payment();
        mockPayment.setId(1L);
        mockPayment.setUserId(userId);
        mockPayment.setPixKey("generated-pix-key");

        when(paymentService.createPayment(userId, "generated-pix-key")).thenReturn(mockPayment);

        ResponseEntity<Payment> response = paymentController.createPayment(userId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockPayment, response.getBody());
    }

    @Test
    public void testCompletePayment() {
        Long paymentId = 1L;
        Payment mockPayment = new Payment();
        mockPayment.setId(paymentId);
        mockPayment.setStatus("COMPLETED");

        when(paymentService.completePayment(paymentId)).thenReturn(mockPayment);

        ResponseEntity<Payment> response = paymentController.completePayment(paymentId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockPayment, response.getBody());
    }
}
