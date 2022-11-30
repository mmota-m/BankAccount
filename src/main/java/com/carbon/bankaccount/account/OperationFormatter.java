package com.carbon.bankaccount.account;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class OperationFormatter {
    public String LIBELLE_SOLDE = "Solde = ";

    DecimalFormat balanceFormat = new DecimalFormat();

    public OperationFormatter(){
        balanceFormat.setMaximumFractionDigits(2);
        balanceFormat.setMinimumFractionDigits(2);
    }
    public String formatAccountInformation(List<Operation> operations, BigDecimal balance) {
        return formatBalance(balance);
    }

    private String formatBalance(BigDecimal balance) {
        return  LIBELLE_SOLDE + balanceFormat.format(balance) ;
    }


}
