package com.learn.stripepay.paymentservice.kafka.consumer;

import com.learn.common.events.UserEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserEventConsumer {

    private final Logger log = LoggerFactory.getLogger(UserEventConsumer.class);
    @KafkaListener(
            topics = "user-events",
            groupId = "payment-group"
    )
    public void consume(UserEvent event) {

        log.info("Receive USER_CREATED: {}", event);
       // System.out.println("Received USER_CREATED  " + event);
        // Prepare payment profile / cache customerId
    }
}
