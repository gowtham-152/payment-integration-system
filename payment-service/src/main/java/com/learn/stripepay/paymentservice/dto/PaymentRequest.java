package com.learn.stripepay.paymentservice.dto;

import lombok.Data;

@Data
public class PaymentRequest {

    private Long orderId;
    private Double amount;
    private String productName;
    private String currency = "USD";
}
