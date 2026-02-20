package com.learn.stripepay.paymentservice.kafka.config;

import com.learn.common.events.OrderEvent;
import com.learn.common.events.UserEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    private Map<String, Object> baseConfig() {

        Map<String, Object> config = new HashMap<>();

        config.put(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "localhost:9092"
        );

        config.put(
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class
        );

        config.put(
                ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,
                "earliest"
        );

        return config;
    }

    @Bean
    public ConsumerFactory<String, UserEvent> userConsumerFactory() {

        JsonDeserializer<UserEvent> deserializer =
                new JsonDeserializer<>(UserEvent.class);

        deserializer.addTrustedPackages("*");

        Map<String, Object> config = baseConfig();
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "payment-group");

        return new DefaultKafkaConsumerFactory<>(
                config,
                new StringDeserializer(),
                deserializer
        );
    }

    @Bean
    public ConsumerFactory<String, OrderEvent> orderConsumerFactory() {

        JsonDeserializer<OrderEvent> deserializer =
                new JsonDeserializer<>(OrderEvent.class);

        deserializer.addTrustedPackages("*");

        Map<String, Object> config = baseConfig();
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "payment-group");

        return new DefaultKafkaConsumerFactory<>(
                config,
                new StringDeserializer(),
                deserializer
        );
    }
}
