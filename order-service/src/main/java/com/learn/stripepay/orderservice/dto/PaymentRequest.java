package com.learn.stripepay.orderservice.dto;

import lombok.Data;

@Data
public class PaymentRequest {

    private Long orderId;
    private Double amount;
    private String productName;
    private String currency;
}
