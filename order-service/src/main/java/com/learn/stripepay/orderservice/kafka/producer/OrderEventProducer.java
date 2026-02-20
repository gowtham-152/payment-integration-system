package com.learn.stripepay.orderservice.kafka.producer;

import com.learn.common.events.EventType;
import com.learn.common.events.OrderEvent;
import com.learn.stripepay.orderservice.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class OrderEventProducer {

    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    private static final String TOPIC = "order-events";

    public void publishOrderCreated(Order order) {

        OrderEvent event = OrderEvent.builder()
                .eventType(EventType.ORDER_CREATED)
                .orderId(String.valueOf(order.getId()))
                .amount(order.getAmount())
                .build();

        kafkaTemplate.send(TOPIC, event);
    }
}
