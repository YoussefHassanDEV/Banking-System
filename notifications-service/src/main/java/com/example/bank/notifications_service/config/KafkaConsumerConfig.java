package com.example.bank.notifications_service.config;

import com.example.bank.notifications_service.events.AccountCreatedEvent;
import com.example.bank.notifications_service.events.PaymentInitiatedEvent;
import com.example.bank.notifications_service.events.PaymentSettledEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrap;

    private Map<String, Object> baseProps() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrap);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        // value deserializer is provided per-factory below
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        return props;
    }

    private <T> ConsumerFactory<String, T> factoryFor(Class<T> clazz) {
        JsonDeserializer<T> jd = new JsonDeserializer<>(clazz);
        jd.addTrustedPackages("com.example.bank.*");
        jd.ignoreTypeHeaders(); // use the configured target type regardless of headers
        return new DefaultKafkaConsumerFactory<>(baseProps(), new StringDeserializer(), jd);
    }

    private <T> ConcurrentKafkaListenerContainerFactory<String, T> containerFor(ConsumerFactory<String, T> cf) {
        ConcurrentKafkaListenerContainerFactory<String, T> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(cf);
        factory.setConcurrency(1);
        return factory;
    }

    @Bean
    public ConsumerFactory<String, AccountCreatedEvent> accountCreatedCF() {
        return factoryFor(AccountCreatedEvent.class);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, AccountCreatedEvent> accountCreatedLCF(
            ConsumerFactory<String, AccountCreatedEvent> cf) {
        return containerFor(cf);
    }

    @Bean
    public ConsumerFactory<String, PaymentInitiatedEvent> paymentInitiatedCF() {
        return factoryFor(PaymentInitiatedEvent.class);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PaymentInitiatedEvent> paymentInitiatedLCF(
            ConsumerFactory<String, PaymentInitiatedEvent> cf) {
        return containerFor(cf);
    }

    @Bean
    public ConsumerFactory<String, PaymentSettledEvent> paymentSettledCF() {
        return factoryFor(PaymentSettledEvent.class);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PaymentSettledEvent> paymentSettledLCF(
            ConsumerFactory<String, PaymentSettledEvent> cf) {
        return containerFor(cf);
    }
}
