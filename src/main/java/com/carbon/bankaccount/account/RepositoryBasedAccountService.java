package com.carbon.bankaccount.account;

import com.carbon.bankaccount.account.helpers.AccountServiceHelper;
import com.carbon.bankaccount.exceptions.ErrorAmountOperationException;
import com.carbon.bankaccount.exceptions.NotEnoughMoneyForOperationException;

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
        if(!AccountServiceHelper.isAmountValid(amount)){
            throw new ErrorAmountOperationException();
        }
        Optional<BigDecimal> balance = repository.getBalance();
        BigDecimal newBalance = balance.map(bigDecimal -> bigDecimal.add(amount)).orElse(amount);
        createOperation(amount, newBalance);
    }

    @Override
    public void withdraw(BigDecimal amount) throws ErrorAmountOperationException, NotEnoughMoneyForOperationException {
        if(!AccountServiceHelper.isAmountValid(amount)) {
            throw new ErrorAmountOperationException();
        }
        Optional<BigDecimal> balance = repository.getBalance();
        BigDecimal actualBalanceValue = balance.orElse(BigDecimal.ZERO);
        if(!AccountServiceHelper.isAccountBalanceEnoughForWithdraw(amount, actualBalanceValue)){
            throw new NotEnoughMoneyForOperationException();
        }
        BigDecimal newBalance = actualBalanceValue.subtract(amount);
        createOperation(BigDecimal.ZERO.subtract(amount), newBalance);
    }

    private void createOperation(BigDecimal amount, BigDecimal balance) {
        BigDecimal newBalance = balance.setScale(2, RoundingMode.HALF_EVEN);
        Operation operation = new Operation(LocalDateTime.now(clock), amount, newBalance);
        repository.create(operation);
    }

}
