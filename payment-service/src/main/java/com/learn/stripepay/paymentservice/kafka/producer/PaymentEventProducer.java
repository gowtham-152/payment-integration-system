package com.learn.stripepay.paymentservice.kafka.producer;

import com.learn.common.events.EventType;
import com.learn.common.events.PaymentEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentEventProducer {

    private final KafkaTemplate<String, PaymentEvent> kafkaTemplate;

    private final Logger logger = LoggerFactory.getLogger(PaymentEventProducer.class);

    private static final String TOPIC = "payment-events";

    public void publishPaymentSuccess(
            String orderId,
            Long userId,
            String paymentId
    ) {

        PaymentEvent event = PaymentEvent.builder()
                .eventType(EventType.PAYMENT_SUCCESS)
                .orderId(orderId)
                .userId(userId)
                .paymentId(paymentId)
                .status("SUCCESS")
                .build();

        kafkaTemplate.send(TOPIC, event);

        logger.info("Send Payment Success Event : {}", event);
       // System.out.println("Payment Success Event → " + event);
    }

    public void publishPaymentFailed(
            String orderId,
            Long userId
    ) {

        PaymentEvent event = PaymentEvent.builder()
                .eventType(EventType.PAYMENT_FAILED)
                .orderId(orderId)
                .userId(userId)
                .status("FAILED")
                .build();

        kafkaTemplate.send(TOPIC, event);
    }
}
