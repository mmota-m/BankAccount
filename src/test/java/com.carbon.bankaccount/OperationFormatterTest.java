package com.carbon.bankaccount;

import com.carbon.bankaccount.account.OperationFormatter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static com.carbon.bankaccount.OperationTestSample.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Operation Formatter Test should")
public class OperationFormatterTest {

    private final OperationFormatter formatter;

    public OperationFormatterTest() {
        this.formatter = new OperationFormatter();
    }

    @Test
    @DisplayName("return a formatted balance")
    void returnOnlyBalanceOfAccount(){
        BigDecimal balance = BigDecimal.ZERO.setScale(2);
        String actual = formatter.formatAccountInformation(List.of(), BigDecimal.ZERO);
        assertEquals(strSolde + balance, actual);
    }

}
