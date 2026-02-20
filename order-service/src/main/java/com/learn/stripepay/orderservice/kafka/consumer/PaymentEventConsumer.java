package com.learn.stripepay.orderservice.kafka.consumer;

import com.learn.common.events.EventType;
import com.learn.common.events.PaymentEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentEventConsumer {

    private final Logger logger = LoggerFactory.getLogger(PaymentEventConsumer.class);
    @KafkaListener(
            topics = "payment-events",
            groupId = "order-group"
    )
    public void consumePaymentEvent(PaymentEvent event) {

        logger.info("Receive Payment Event : {}", event);

        if (event.getEventType() == EventType.PAYMENT_SUCCESS) {

            logger.info("Order Paid: {}", event.getOrderId());
        }

        if (event.getEventType() == EventType.PAYMENT_FAILED) {

            logger.info("Payment Failed: {}", event.getOrderId());

        }
    }
}
