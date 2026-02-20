package com.learn.common.events;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentEvent {

    private EventType eventType;
    private String orderId;
    private Long userId;
    private String paymentId;
    private String status;
}
