package com.learn.stripepay.paymentservice.repository;

import com.learn.stripepay.paymentservice.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository
        extends JpaRepository<Payment, Long> {

    Optional<Payment>
    findByStripeSessionId(String sessionId);
}
