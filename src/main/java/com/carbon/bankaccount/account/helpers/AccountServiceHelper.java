package com.carbon.bankaccount.account.helpers;

import java.math.BigDecimal;

public class AccountServiceHelper {

    public static boolean isAccountBalanceEnoughForWithdraw(BigDecimal amount, BigDecimal actualBalanceValue) {
        if(actualBalanceValue.compareTo(BigDecimal.ZERO) <= 0){
            return false;
        }
        if(actualBalanceValue.compareTo(amount) <= 0){
            return false;
        }
        return true;
    }

    public static boolean isAmountValid(BigDecimal amount) {
        return amount.compareTo(BigDecimal.ZERO) > 0;
    }
}
