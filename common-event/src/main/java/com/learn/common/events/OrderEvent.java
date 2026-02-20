package com.learn.common.events;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderEvent {

    private EventType eventType;
    private String orderId;
    private Long userId;
    private Double amount;
}
