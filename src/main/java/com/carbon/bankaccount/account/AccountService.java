package com.carbon.bankaccount.account;

import com.carbon.bankaccount.exceptions.ErrorAmountOperationException;
import com.carbon.bankaccount.exceptions.NotEnoughMoneyForOperationException;

import java.math.BigDecimal;

public interface AccountService {

    void makeADeposit(BigDecimal amount) throws ErrorAmountOperationException;

    void withdraw(BigDecimal amount) throws ErrorAmountOperationException, NotEnoughMoneyForOperationException;

    void displayOperations();

}
