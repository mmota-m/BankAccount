package com.carbon.bankaccount;

import com.carbon.bankaccount.account.OperationFormatter;
import com.carbon.bankaccount.account.OperationPrinter;
import org.itsallcode.io.Capturable;
import org.itsallcode.junit.sysextensions.SystemOutGuard;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SystemOutGuard.class)
@ExtendWith(MockitoExtension.class)
@DisplayName("Operation Printer Test Should")
public class OperationPrinterTest {

    @Mock
    private OperationFormatter formatter;

    @InjectMocks
    private OperationPrinter printer;

    @Test
    @DisplayName("display formatted operations")
    void printText(final Capturable stream){
        final String expected = "This text is expected.";
        when(formatter.formatAccountInformation(any(), any())).thenReturn(expected);

        stream.capture();
        printer.printOperations(List.of(), BigDecimal.ZERO);

        verify(formatter).formatAccountInformation(List.of(), BigDecimal.ZERO);
        verifyNoMoreInteractions(formatter);
        assertEquals(stream.getCapturedData(), expected);
    }
}
