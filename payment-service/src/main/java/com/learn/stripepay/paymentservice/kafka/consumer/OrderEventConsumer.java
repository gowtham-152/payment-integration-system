package com.learn.stripepay.paymentservice.kafka.consumer;

import com.learn.common.events.OrderEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderEventConsumer {

    private final Logger log = LoggerFactory.getLogger(OrderEventConsumer.class);
    @KafkaListener(
            topics = "order-events",
            groupId = "payment-group"
    )
    public void consume(OrderEvent event) {

        log.info("Receive Order Event : {}", event);
      //  System.out.println("Received ORDER_CREATED → " + event);
        // Trigger Stripe checkout creation
    }
}
