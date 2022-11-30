package com.carbon.bankaccount.account;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class OperationFormatter {

    private String LIBELLE_AMOUNT = "Amount";
    private String LIBELLE_DATE = "Date";
    private String LIBELLE_BALANCE = "Balance";
    private String LIBELLE_OPERATION = " " + LIBELLE_DATE + "\t\t\t| " + LIBELLE_AMOUNT + "\t| "+ LIBELLE_BALANCE + "\n";
    private String LIBELLE_SOLDE = "Solde = ";

    private DecimalFormat balanceFormat = new DecimalFormat();
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public OperationFormatter(){
        balanceFormat.setMaximumFractionDigits(2);
        balanceFormat.setMinimumFractionDigits(2);
    }
    public String formatAccountInformation(List<Operation> operations, BigDecimal balance) {
        String stringOperations = formatOperations(operations);
        String stringBalance = formatBalance(balance);
        return stringOperations + stringBalance ;
    }

    private String formatOperations(List<Operation> operations) {
        if (operations.isEmpty()) {
            return "";
        }
        return operations.stream()
                .map(this::formatAnOperation)
                .collect(Collectors.joining("\n", LIBELLE_OPERATION, "\n"));
    }

    private String formatAnOperation(Operation operation){
        return operation.getDate().format(dateTimeFormatter) + "|\t\t" +
                operation.getAmount() + "\t|\t\t" +
                operation.getBalance();
    }

    private String formatBalance(BigDecimal balance) {
        return  LIBELLE_SOLDE + balanceFormat.format(balance) ;
    }


}
