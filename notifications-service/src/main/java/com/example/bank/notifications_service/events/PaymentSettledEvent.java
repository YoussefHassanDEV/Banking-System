package com.example.bank.notifications_service.events;

import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
public class PaymentSettledEvent {
    private String paymentId;
    private BigDecimal amount;
    private String currency;
    private Instant timestamp;
    private String status;
}