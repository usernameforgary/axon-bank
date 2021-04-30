package com.example.axonbank.controller;

import com.example.axonbank.command.CreateAccountCommand;
import com.example.axonbank.command.DepositMoneyCommand;
import com.example.axonbank.command.WithdrawMoneyCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;


@RestController
public class AccountController {
    private static final Logger log = LoggerFactory.getLogger(AccountController.class);

    private final CommandGateway commandGateway;

    @Autowired
    public AccountController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping("/accounts")
    public CompletableFuture<Object> createBankAccount(@RequestBody String name) {
        log.info("Request to create account for {}", name);

        UUID accountId = UUID.randomUUID();
        return commandGateway.send(new CreateAccountCommand(accountId, name));
    }

    @PutMapping("/account/{accountId}/deposit/{amount}")
    public CompletableFuture<Object> depositBankAccount(@PathVariable UUID accountId, @PathVariable double amount) {
        log.info("Request to deposit {} dollar for account {}", amount, accountId);

        return commandGateway.send(new DepositMoneyCommand(accountId, amount));
    }

    @PutMapping("/account/{accountId}/withdraw/{amount}")
    public CompletableFuture<Object> withDrawBankAccount(@PathVariable UUID accountId, @PathVariable double amount) {
        log.info("Request to withdraw {} dollar for account {}", amount, accountId);
        return commandGateway.send(new WithdrawMoneyCommand(accountId, amount));
    }
}
