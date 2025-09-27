package com.example.bank.notifications_service.events;


import lombok.Data;

import java.time.Instant;

@Data
public class AccountCreatedEvent {
    private Long accountId;
    private String owner;
    private String currency;
    private Instant timestamp;
}