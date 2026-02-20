package com.learn.common.events;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEvent {

    private EventType eventType;
    private Long userId;
    private String email;
    private String stripeCustomerId;
}
