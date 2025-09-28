package com.example.bank.notifications_service.listeners;

import com.example.bank.notifications_service.events.AccountCreatedEvent;
import com.example.bank.notifications_service.events.PaymentInitiatedEvent;
import com.example.bank.notifications_service.events.PaymentSettledEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class NotificationListener {

    @KafkaListener(
            topics = "accounts.account-created.v1",
            groupId = "notification")
    public void onAccountCreated(@Payload(required = false) AccountCreatedEvent event,
                                 @Header(value = KafkaHeaders.RECEIVED_KEY, required = false) String key,
                                 Acknowledgment ack) {
        if (event == null) {
            log.warn("Received tombstone for account deletion, key: {}", key);
            ack.acknowledge();
            return;
        }

        log.info("🎉 [Notify] Account created: id={} owner={} currency={}",
                event.getAccountId(), event.getOwner(), event.getCurrency());
        ack.acknowledge();
    }

    @KafkaListener(
            topics = "payments.payment-initiated.v1",
            groupId = "notification")
    public void onPaymentInitiated(@Payload(required = false) PaymentInitiatedEvent event,
                                   @Header(value = KafkaHeaders.RECEIVED_KEY, required = false) String key,
                                   Acknowledgment ack) {
        if (event == null) {
            log.warn("Received tombstone for payment deletion, key: {}", key);
            ack.acknowledge();
            return;
        }

        log.info("💰 [Notify] Payment initiated: id={} amount={} {}",
                event.getPaymentId(), event.getAmount(), event.getCurrency());
        ack.acknowledge();
    }

    @KafkaListener(
            topics = "payments.payment-settled.v1",
            groupId = "notification")
    public void onPaymentSettled(@Payload(required = false) PaymentSettledEvent event,
                                 @Header(value = KafkaHeaders.RECEIVED_KEY, required = false) String key,
                                 Acknowledgment ack) {
        if (event == null) {
            log.warn("Received tombstone for payment deletion, key: {}", key);
            ack.acknowledge();
            return;
        }

        log.info("✅ [Notify] Payment settled: id={} status={}",
                event.getPaymentId(), event.getStatus());
        ack.acknowledge();
    }
}
