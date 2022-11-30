package com.carbon.bankaccount;

import com.carbon.bankaccount.account.Operation;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OperationTestSample {

    public static final Operation sampleOperation = new Operation(
            LocalDateTime.now(),
            BigDecimal.ZERO,
            BigDecimal.ZERO
    );

    public static final Operation sampleOperationWithdraw = new Operation(
            LocalDateTime.now(),
            BigDecimal.valueOf(-300),
            BigDecimal.ZERO
    );

    public static String strSolde = "Solde = ";

    public static BigDecimal bgBalance = BigDecimal.valueOf(500);
}
