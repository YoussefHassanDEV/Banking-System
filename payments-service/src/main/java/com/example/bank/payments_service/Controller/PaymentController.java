package com.example.bank.payments_service.Controller;

import com.example.bank.payments_service.DTOs.PaymentInitiatedEvent;
import com.example.bank.payments_service.events.PaymentSettledEvent;
import com.example.bank.payments_service.model.Payment;
import com.example.bank.payments_service.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentRepository repo;
    private final KafkaTemplate<String, Object> kafka;
    private final String topicInitiated;
    private final String topicSettled;

    public PaymentController(PaymentRepository repo,
                             KafkaTemplate<String, Object> kafka,
                             @Value("${app.topics.paymentInitiated}") String topicInitiated,
                             @Value("${app.topics.paymentSettled}") String topicSettled) {
        this.repo = repo;
        this.kafka = kafka;
        this.topicInitiated = topicInitiated;
        this.topicSettled = topicSettled;
    }

    @PostMapping("/initiate")
    public Payment initiate(@RequestBody Payment req) {
        req.setStatus("INITIATED");
        Payment saved = repo.save(req);

        PaymentInitiatedEvent evt = new PaymentInitiatedEvent();
        evt.setPaymentId(saved.getPaymentId());
        evt.setFromAccountId(saved.getFromAccountId());
        evt.setToAccountId(saved.getToAccountId());
        evt.setAmount(saved.getAmount());
        evt.setCurrency(saved.getCurrency());

        kafka.send(topicInitiated, saved.getPaymentId(), evt);
        return saved;
    }

    @PostMapping("/{id}/settle")
    public Payment settle(@PathVariable Long id) {
        Payment p = repo.findById(id).orElseThrow();
        p.setStatus("SETTLED");
        Payment saved = repo.save(p);

        PaymentSettledEvent evt = new PaymentSettledEvent();
        evt.setPaymentId(saved.getPaymentId());
        evt.setAmount(saved.getAmount());
        evt.setCurrency(saved.getCurrency());

        kafka.send(topicSettled, saved.getPaymentId(), evt);
        return saved;
    }

    @GetMapping("/{id}")
    public Payment get(@PathVariable Long id) {
        return repo.findById(id).orElseThrow();
    }
}
