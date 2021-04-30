package com.example.axonbank.domain;

import com.example.axonbank.command.CreateAccountCommand;
import com.example.axonbank.command.DepositMoneyCommand;
import com.example.axonbank.command.WithdrawMoneyCommand;
import com.example.axonbank.event.AccountCreatedEvent;
import com.example.axonbank.event.MoneyDepositedEvent;
import com.example.axonbank.event.MoneyWithdrawnEvent;
import com.example.axonbank.exception.BusinessException;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.UUID;

@Aggregate
public class Account {
    @AggregateIdentifier
    private UUID accountId;
    private Double balance;

    public Account() {}

    @CommandHandler
    public Account(CreateAccountCommand command) {
        AggregateLifecycle.apply(new AccountCreatedEvent(command.getAccountId(), command.getName()));
    }

    @EventSourcingHandler
    protected void on(AccountCreatedEvent event) {
        accountId = event.getAccountId();
        balance = 0.0;
    }

    @CommandHandler
    public void handle(DepositMoneyCommand command) {
        AggregateLifecycle.apply(new MoneyDepositedEvent(command.getAccountId(), command.getAmount()));
    }

    @EventSourcingHandler
    public void on(MoneyDepositedEvent event) {
        this.balance = balance + event.getAmount();
    }

    @CommandHandler
    public void handle(WithdrawMoneyCommand command) {
        if(balance - command.getAmount() >=0) {
            AggregateLifecycle.apply(new MoneyWithdrawnEvent(command.getAccountId(), command.getAmount()));
        } else {
            throw new BusinessException("余额不足");
        }
    }

    @EventSourcingHandler
    public void handle(MoneyWithdrawnEvent event) {
       this.balance = balance - event.getAmount();
    }
}
