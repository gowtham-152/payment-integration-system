package com.learn.stripepay.paymentservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_payments")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;

    private String stripeSessionId;

    private String paymentIntentId;

    private Double amount;

    private String currency;

    private String status;

    private LocalDateTime createdAt;
}
