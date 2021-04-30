package com.example.axonbank.repository;

import com.example.axonbank.domain.AccountView;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BankAccountRepository extends JpaRepository<AccountView, UUID> {
}
