package com.fiap.ecommerce.payment.controller;

import com.fiap.ecommerce.payment.model.Payment;
import com.fiap.ecommerce.payment.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<Payment> createPayment(@RequestParam Long userId) throws IOException {
        Payment createdPayment = paymentService.createPayment(userId, "generated-pix-key");
        return ResponseEntity.ok(createdPayment);
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<Payment> completePayment(@PathVariable Long id) {
        Payment completedPayment = paymentService.completePayment(id);
        return ResponseEntity.ok(completedPayment);
    }
}