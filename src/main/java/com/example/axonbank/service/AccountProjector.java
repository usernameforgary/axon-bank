package com.example.axonbank.service;

import com.example.axonbank.domain.AccountView;
import com.example.axonbank.event.AccountCreatedEvent;
import com.example.axonbank.event.MoneyDepositedEvent;
import com.example.axonbank.event.MoneyWithdrawnEvent;
import com.example.axonbank.repository.BankAccountRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountProjector {
    private final BankAccountRepository repository;

    @Autowired
    public AccountProjector(BankAccountRepository repository) {
        this.repository = repository;
    }

    @EventHandler
    public void on(AccountCreatedEvent event) {
        AccountView accountView = new AccountView();
        accountView.setAccountId(event.getAccountId());
        accountView.setName(event.getName());

        repository.save(accountView);
    }

    @EventHandler
    public void on(MoneyDepositedEvent evt) {
        AccountView accountView = repository.getOne(evt.getAccountId());

        Double newBalance = accountView.getBalance() + evt.getAmount();
        accountView.setBalance(newBalance);

        repository.save(accountView);
    }

    @EventHandler
    public void on(MoneyWithdrawnEvent evt) {
        AccountView accountView = repository.getOne(evt.getAccountId());

        Double newBalance = accountView.getBalance() - evt.getAmount();
        accountView.setBalance(newBalance);

        repository.save(accountView);
    }
}
