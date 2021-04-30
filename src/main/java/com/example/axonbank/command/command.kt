package com.example.axonbank.command

import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.util.*

data class CreateAccountCommand(
        @TargetAggregateIdentifier
        val accountId: UUID,
        val name: String?
)

data class DepositMoneyCommand(
        @TargetAggregateIdentifier
        val accountId: UUID,
        val amount: Double
)

data class WithdrawMoneyCommand(
        @TargetAggregateIdentifier
        val accountId: UUID,
        val amount: Double
)