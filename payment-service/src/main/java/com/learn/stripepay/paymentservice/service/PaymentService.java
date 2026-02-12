package com.learn.stripepay.paymentservice.service;

import com.learn.stripepay.paymentservice.dto.PaymentRequest;
import com.learn.stripepay.paymentservice.dto.PaymentResponse;
import com.learn.stripepay.paymentservice.entity.Payment;
import com.learn.stripepay.paymentservice.repository.PaymentRepository;
import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository repo;

    @Value("${stripe.secret.key}")
    private String stripeKey;

    @Value("${stripe.success.url}")
    private String successUrl;

    @Value("${stripe.cancel.url}")
    private String cancelUrl;

    public PaymentResponse createPaymentSession(
            PaymentRequest request) {

        try {
            Stripe.apiKey = stripeKey;

            SessionCreateParams params =
                    SessionCreateParams.builder()
                            .setMode(SessionCreateParams.Mode.PAYMENT)
                            .setSuccessUrl(successUrl)
                            .setCancelUrl(cancelUrl)

                            // Metadata (IMPORTANT)
                            .putMetadata(
                                    "orderId",
                                    request.getOrderId().toString()
                            )

                            .addLineItem(
                                    SessionCreateParams
                                            .LineItem.builder()
                                            .setQuantity(1L)
                                            .setPriceData(
                                                    SessionCreateParams
                                                            .LineItem
                                                            .PriceData.builder()
                                                            .setCurrency(
                                                                    request.getCurrency())
                                                            .setUnitAmount(
                                                                    (long) (request.getAmount() * 100))
                                                            .setProductData(
                                                                    SessionCreateParams
                                                                            .LineItem
                                                                            .PriceData
                                                                            .ProductData
                                                                            .builder()
                                                                            .setName(
                                                                                    request.getProductName())
                                                                            .build())
                                                            .build())
                                            .build())
                            .build();

            Session session =
                    Session.create(params);

            Payment payment = Payment.builder()
                    .orderId(request.getOrderId())
                    .stripeSessionId(session.getId())
                    .amount(request.getAmount())
                    .currency(request.getCurrency())
                    .status("CREATED")
                    .createdAt(LocalDateTime.now())
                    .build();

            repo.save(payment);


            return PaymentResponse.builder()
                    .checkoutUrl(session.getUrl())
                    .sessionId(session.getId())
                    .build();

        } catch (Exception e) {
            throw new RuntimeException(
                    "Error creating Stripe session", e);
        }
    }
}
