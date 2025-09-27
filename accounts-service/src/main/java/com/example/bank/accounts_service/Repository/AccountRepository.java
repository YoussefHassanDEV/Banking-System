package com.example.bank.accounts_service.Repository;

import com.example.bank.accounts_service.Model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {}