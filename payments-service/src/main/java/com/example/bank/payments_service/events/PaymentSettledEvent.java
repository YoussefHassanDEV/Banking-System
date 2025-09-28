package com.example.bank.payments_service.events;


import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
public class PaymentSettledEvent {
    private String paymentId;
    private BigDecimal amount;
    private String currency;
    private String status = "SETTLED";
    private Instant timestamp = Instant.now();
}