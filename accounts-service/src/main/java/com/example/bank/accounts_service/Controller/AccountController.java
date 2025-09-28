package com.example.bank.accounts_service.Controller;

import com.example.bank.accounts_service.DTOs.AccountCreatedEvent;
import com.example.bank.accounts_service.Model.Account;
import com.example.bank.accounts_service.Repository.AccountRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    private final AccountRepository repo;
    private final KafkaTemplate<String, Object> kafka;
    private final String topic;
    public AccountController(AccountRepository repo,
                             KafkaTemplate<String, Object> kafka,
                             @Value("${app.topics.accountCreated}") String topic) {
        this.repo = repo;
        this.kafka = kafka;
        this.topic = topic;
    }

    @PostMapping
    public Account create(@RequestBody Account req) {
        Account saved = repo.save(req);

        AccountCreatedEvent evt = new AccountCreatedEvent();
        evt.setAccountId(saved.getId());
        evt.setOwner(saved.getOwner());
        evt.setCurrency(saved.getCurrency());

        kafka.send(topic, String.valueOf(saved.getId()), evt);
        return saved;
    }

    @GetMapping("/{id}")
    public Account get(@PathVariable Long id) {
        return repo.findById(id).orElseThrow();
    }
}
