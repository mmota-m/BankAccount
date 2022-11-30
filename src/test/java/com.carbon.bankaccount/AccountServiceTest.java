package com.carbon.bankaccount;

import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("create a new operation when deposit")
    @ValueSource(doubles =  {2, 445.001, 5.00})
    void createOperationWhenMakingADeposit(Double amount){
        initClock();
        BigDecimal bgAmount = BigDecimal.valueOf(amount);

        when(repository.getBalance()).thenReturn(Optional.of(bgBalance));
        when(repository.create(any())).thenReturn(sampleOperation);

        this.service.makeADeposit(bgAmount);

        InOrder order = inOrder(repository);
        order.verify(repository).getBalance();
        order.verify(repository).create(
                new Operation(LocalDateTime.now(fixedClock), bgAmount,
                        bgBalance.add(bgAmount).setScale(2, RoundingMode.HALF_EVEN)));
        order.verifyNoMoreInteractions();
    }

}
