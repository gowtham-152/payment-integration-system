package com.learn.stripepay.userservice.kafka.producer;

import com.learn.common.events.EventType;
import com.learn.common.events.UserEvent;
import com.learn.stripepay.userservice.entity.User;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserEventProducer {

    private final KafkaTemplate<String, UserEvent> kafkaTemplate;

    private final Logger log = LoggerFactory.getLogger(UserEventProducer.class);

    private static final String TOPIC = "user-events";

    public void publishUserCreated(User user) {

        UserEvent event = UserEvent.builder()
                .eventType(EventType.USER_CREATED)
                .userId(user.getId())
                .email(user.getEmail())
                .stripeCustomerId(user.getStripeCustomerId())
                .build();

        kafkaTemplate.send(TOPIC, event);

        log.info("User created successfully"+event.toString());
       // System.out.println("User Created Event → " + event);

    }
}
