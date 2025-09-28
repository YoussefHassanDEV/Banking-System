package com.example.bank.payments_service.repository;

import com.example.bank.payments_service.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {}
