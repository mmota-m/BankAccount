package com.carbon.bankaccount;

import com.carbon.bankaccount.account.OperationFormatter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.carbon.bankaccount.OperationTestSample.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Operation Formatter Test should")
public class OperationFormatterTest {

    private final OperationFormatter formatter;

    public OperationFormatterTest() {
        this.formatter = new OperationFormatter();
    }

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Test
    @DisplayName("return a formatted balance")
    void returnOnlyBalanceOfAccount(){
        BigDecimal balance = BigDecimal.ZERO.setScale(2);
        String actual = formatter.formatAccountInformation(List.of(), BigDecimal.ZERO);
        assertEquals(strSolde + balance, actual);
    }

    @Test
    @DisplayName("return all information formatted when deposit")
    void returnFormattedAccountInformation(){
        String actual = formatter.formatAccountInformation(List.of(sampleOperation), BigDecimal.ZERO);
        String expected = new StringBuilder(" Date			| Amount	| Balance\n")
                .append(LocalDateTime.now().format(dateTimeFormatter))
                .append("|		0	|		0\nSolde = 0.00").toString();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("return all information formatted when withdraw")
    void returnFormattedAccountInformationWithdraw(){
        String actual = formatter.formatAccountInformation(List.of(sampleOperationWithdraw), bgBalance);
        String expected = new StringBuilder(" Date			| Amount	| Balance\n")
                .append(LocalDateTime.now().format(dateTimeFormatter))
                .append("|		-300	|		0\nSolde = 500.00").toString();
        assertEquals(expected, actual);
    }

}
