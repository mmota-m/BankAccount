package com.carbon.bankaccount;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Optional;
public class RepositoryBasedAccountService implements AccountService {

   private final OperationRepository repository;

    private final Clock clock;

    public RepositoryBasedAccountService(OperationRepository operationRepository, Clock clock) {
        this.repository = operationRepository;
        this.clock = clock;
    }

    @Override
    public void makeADeposit(BigDecimal amount) {
        Optional<BigDecimal> balanceOptional = repository.getBalance();
        BigDecimal balance = balanceOptional.map(bigDecimal -> bigDecimal.add(amount)).orElse(amount);
        BigDecimal newBalance = balance.setScale(2, RoundingMode.HALF_EVEN);
        Operation operation = new Operation(LocalDateTime.now(clock), amount, newBalance);
        repository.create(operation);
    }

}
