package com.carbon.bankaccount;

import java.math.BigDecimal;
import java.util.Optional;

public interface OperationRepository {

    Operation create(Operation operation);

    Optional<BigDecimal> getBalance();
}
