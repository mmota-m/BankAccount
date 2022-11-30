package com.carbon.bankaccount.helpers;

import com.carbon.bankaccount.account.helpers.AccountServiceHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Account Service Helper Should")
public class AccountServiceHelperTest {

    @ParameterizedTest
    @DisplayName("return false given an amount higher than the balance")
    @CsvSource(value = {
            "29.0:30.00",
            "30.0:30.001",
            "2.338:80.33"
    }, delimiter = ':')
    void returnFalseGivenAnAmountHigherThanTheBalance(Double balance, Double amount) {
        BigDecimal balanceValue = BigDecimal.valueOf(balance);
        BigDecimal amountValue = BigDecimal.valueOf(amount);
        assertFalse(AccountServiceHelper.isAccountBalanceEnoughForWithdraw(amountValue, balanceValue));
    }

    @ParameterizedTest
    @DisplayName("return true given an amount lower than the balance")
    @CsvSource(value = {
            "30.001:30.00",
            "85:80.33"
    }, delimiter = ':')
    void returnTrueGivenAnAmountInferiorThanTheBalance(Double balance, Double amount) {
        BigDecimal balanceValue = BigDecimal.valueOf(balance);
        BigDecimal amountValue = BigDecimal.valueOf(amount);
        assertTrue(AccountServiceHelper.isAccountBalanceEnoughForWithdraw(amountValue, balanceValue));
    }

    @ParameterizedTest
    @DisplayName("return true given an amount higher than 0")
    @CsvSource(value = {
            "30.001",
            "85"
    })
    void returnTrueGivenAValidAmount(Double amount){
        BigDecimal amountValue = BigDecimal.valueOf(amount);
        assertTrue(AccountServiceHelper.isAmountValid(amountValue));
    }

    @ParameterizedTest
    @DisplayName("return true given an amount lower than 0")
    @CsvSource(value = {
            "0",
            "-2"
    }, delimiter = ':')
    void returnFalseGivenAnInvalidAmount(Double amount) {
        BigDecimal amountValue = BigDecimal.valueOf(amount);
        assertFalse(AccountServiceHelper.isAmountValid(amountValue));
    }


}
