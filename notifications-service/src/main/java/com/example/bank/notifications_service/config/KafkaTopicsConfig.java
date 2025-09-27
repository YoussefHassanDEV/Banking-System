package com.example.bank.notifications_service.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicsConfig {
    @Bean
    public NewTopic accountsCreatedDLT() {
        return new NewTopic("accounts.account-created.v1.DLT", 1, (short) 1);
    }
    @Bean
    public NewTopic paymentsInitiatedDLT() {
        return new NewTopic("payments.payment-initiated.v1.DLT", 1, (short) 1);
    }
    @Bean
    public NewTopic paymentsSettledDLT() {
        return new NewTopic("payments.payment-settled.v1.DLT", 1, (short) 1);
    }
}