package com.carbon.bankaccount.account;

import com.carbon.bankaccount.exceptions.ErrorAmountOperationException;

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
    public void makeADeposit(BigDecimal amount) throws ErrorAmountOperationException {
        if(!isAmountValid(amount)){
            throw new ErrorAmountOperationException();
        }
        Optional<BigDecimal> balanceOptional = repository.getBalance();
        BigDecimal balance = balanceOptional.map(bigDecimal -> bigDecimal.add(amount)).orElse(amount);
        BigDecimal newBalance = balance.setScale(2, RoundingMode.HALF_EVEN);
        Operation operation = new Operation(LocalDateTime.now(clock), amount, newBalance);
        repository.create(operation);
    }

    @Override
    public void withdraw(BigDecimal amount) {
        Optional<BigDecimal> balance = repository.getBalance();
        BigDecimal actualBalanceValue = balance.orElse(BigDecimal.ZERO);
        BigDecimal newBalance = actualBalanceValue.subtract(amount);
        newBalance = newBalance.setScale(2, RoundingMode.HALF_EVEN);
        Operation operation = new Operation(LocalDateTime.now(clock), BigDecimal.ZERO.subtract(amount), newBalance);
        repository.create(operation);
    }

    private boolean isAmountValid(BigDecimal amount) {
        return amount.compareTo(BigDecimal.ZERO) > 0;
    }

}
