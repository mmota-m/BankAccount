package com.carbon.bankaccount.account;

import com.carbon.bankaccount.exceptions.ErrorAmountOperationException;

import java.math.BigDecimal;

public interface AccountService {

    void makeADeposit(BigDecimal amount) throws ErrorAmountOperationException;

    void withdraw(BigDecimal amount) throws ErrorAmountOperationException;

}
