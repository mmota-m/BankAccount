package com.carbon.bankaccount.account;

import java.math.BigDecimal;
import java.util.Optional;

public interface OperationRepository {

    Operation create(Operation operation);

    Optional<BigDecimal> getBalance();
}
