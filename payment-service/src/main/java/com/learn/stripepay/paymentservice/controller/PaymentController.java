package com.learn.stripepay.paymentservice.controller;

import com.learn.stripepay.paymentservice.dto.PaymentRequest;
import com.learn.stripepay.paymentservice.dto.PaymentResponse;
import com.learn.stripepay.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    /**
     * Create Stripe Checkout Session
     */
    @PostMapping("/create-checkout-session")
    public ResponseEntity<PaymentResponse> createCheckoutSession(
            @RequestBody PaymentRequest request) {

        PaymentResponse response =
                paymentService.createPaymentSession(request);

        return ResponseEntity.ok(response);
    }
}
