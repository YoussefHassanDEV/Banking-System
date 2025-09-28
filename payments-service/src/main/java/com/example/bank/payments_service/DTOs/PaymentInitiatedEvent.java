package com.example.bank.payments_service.DTOs;

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
    private Instant timestamp = Instant.now();
}