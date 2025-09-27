package com.example.bank.notifications_service.listeners;

import com.example.bank.notifications_service.events.AccountCreatedEvent;
import com.example.bank.notifications_service.events.PaymentInitiatedEvent;
import com.example.bank.notifications_service.events.PaymentSettledEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationListener {

    @KafkaListener(
            topics = "accounts.account-created.v1",
            groupId = "notification",
            containerFactory = "accountCreatedLCF")
    public void onAccountCreated(AccountCreatedEvent e) {
        System.out.printf("[Notify] Account created: id=%d owner=%s currency=%s%n",
                e.getAccountId(), e.getOwner(), e.getCurrency());
    }

    @KafkaListener(
            topics = "payments.payment-initiated.v1",
            groupId = "notification",
            containerFactory = "paymentInitiatedLCF")
    public void onPaymentInitiated(PaymentInitiatedEvent e) {
        System.out.printf("[Notify] Payment initiated: id=%s amount=%s %s%n",
                e.getPaymentId(), e.getAmount(), e.getCurrency());
    }

    @KafkaListener(
            topics = "payments.payment-settled.v1",
            groupId = "notification",
            containerFactory = "paymentSettledLCF")
    public void onPaymentSettled(PaymentSettledEvent e) {
        System.out.printf("[Notify] Payment settled: id=%s status=%s%n",
                e.getPaymentId(), e.getStatus());
    }
}
