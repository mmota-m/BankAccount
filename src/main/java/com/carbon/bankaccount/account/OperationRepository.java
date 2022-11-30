package com.carbon.bankaccount.account;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface OperationRepository {

    List<Operation> findAllSorted();

    Operation create(Operation operation);

    Optional<BigDecimal> getBalance();
}
