package com.example.bank.notifications_service.events;

import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
public class PaymentInitiatedEvent {
    private String paymentId;
    private Long fromAccountId;
    private Long toAccountId;
    private BigDecimal amount;
    private String currency;
    private Instant timestamp;
}