package com.example.bank.notifications_service.config;

import org.apache.kafka.common.TopicPartition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
public class KafkaErrorHandlingConfig {

    @Bean
    public DefaultErrorHandler errorHandler(KafkaOperations<Object, Object> ops) {
        DeadLetterPublishingRecoverer recoverer = new DeadLetterPublishingRecoverer(
                ops,
                (record, ex) -> new TopicPartition(record.topic() + ".DLT", record.partition()));
        // retry 3 times, 2 seconds apart, then send to DLT
        return new DefaultErrorHandler(recoverer, new FixedBackOff(2000L, 3));
    }
}