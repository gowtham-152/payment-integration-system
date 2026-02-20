package com.learn.stripepay.userservice.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic userTopic() {
        return TopicBuilder.name("user-events").build();
    }

    @Bean
    public NewTopic orderTopic() {
        return TopicBuilder.name("order-events").build();
    }

    @Bean
    public NewTopic paymentTopic() {
        return TopicBuilder.name("payment-events").build();
    }
}

