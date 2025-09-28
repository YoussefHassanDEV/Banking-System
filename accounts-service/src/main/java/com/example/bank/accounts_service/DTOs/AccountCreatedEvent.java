package com.example.bank.accounts_service.DTOs;

import lombok.Data;
import java.time.Instant;

@Data
public class AccountCreatedEvent {
    private Long accountId;
    private String owner;
    private String currency;
    private Instant timestamp = Instant.now();
}
