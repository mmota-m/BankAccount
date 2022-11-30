package com.carbon.bankaccount;

import com.carbon.bankaccount.account.Operation;
import com.carbon.bankaccount.account.OperationRepository;
import com.carbon.bankaccount.account.RepositoryBasedAccountService;
import com.carbon.bankaccount.exceptions.ErrorAmountOperationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Account Service Test should")
public class AccountServiceTest {
    @Mock
    private OperationRepository repository;

    @Mock
    private Clock clock;

    @InjectMocks
    private RepositoryBasedAccountService service;
    private Clock fixedClock;

    public static final Operation sampleOperation = new Operation(
            LocalDateTime.now(),
            BigDecimal.ZERO,
            BigDecimal.ZERO
    );

    public static BigDecimal bgBalance = BigDecimal.valueOf(500);


    public void initClock() {
        fixedClock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
        doReturn(fixedClock.instant()).when(clock).instant();
        doReturn(fixedClock.getZone()).when(clock).getZone();
    }

    @ParameterizedTest
    @DisplayName("return no error when deposit given a correct amount")
    @ValueSource(doubles =  {2, 445.001, 5.00})
    void returnNoErrorWhenMakeADeposit(Double amount){
        initClock();
        BigDecimal bgAmount = BigDecimal.valueOf(amount);

        when(repository.getBalance()).thenReturn(Optional.of(bgBalance));
        when(repository.create(any())).thenReturn(sampleOperation);

        assertDoesNotThrow(() -> this.service.makeADeposit(bgAmount));

        InOrder order = inOrder(repository);
        order.verify(repository).getBalance();
        order.verify(repository).create(
                new Operation(LocalDateTime.now(fixedClock), bgAmount,
                        bgBalance.add(bgAmount).setScale(2, RoundingMode.HALF_EVEN)));
        order.verifyNoMoreInteractions();
    }

    @Test
    @DisplayName("return ErrorAmountOperationException when deposit given an amount of 0")
    void returnErrorWhenMakeADepositOf0(){
        assertThrows(ErrorAmountOperationException.class, () -> {
            this.service.makeADeposit(BigDecimal.valueOf(0));
        });
        verifyNoInteractions(repository);
    }

    @ParameterizedTest
    @DisplayName("create an operation when withdraw given an amount")
    @ValueSource(doubles =  {2, 445.001, 5.00})
    void createOperationWhenWithdraw(Double amount){
        initClock();
        BigDecimal bgAmount = BigDecimal.valueOf(amount);

        when(repository.getBalance()).thenReturn(Optional.of(bgBalance));
        when(repository.create(any())).thenReturn(sampleOperation);

        this.service.withdraw(bgAmount);

        InOrder order = inOrder(repository);
        order.verify(repository).getBalance();
        order.verify(repository).create(
                new Operation(LocalDateTime.now(fixedClock), BigDecimal.ZERO.subtract(bgAmount),
                        bgBalance.subtract(bgAmount).setScale(2, RoundingMode.HALF_EVEN)));
        order.verifyNoMoreInteractions();
    }



}
