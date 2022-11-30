package com.carbon.bankaccount.account;

import java.math.BigDecimal;
import java.util.List;

public class OperationPrinter {

    private final OperationFormatter formatter;

    public OperationPrinter(OperationFormatter formatter) {
        this.formatter = formatter;
    }
    public void printOperations(List<Operation> operations, BigDecimal balance) {
        String displayAccountInformation = formatter.formatAccountInformation(operations, balance);
        System.out.print(displayAccountInformation);
    }

}
