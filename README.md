package silicon.transactions.holds.v1.service.incat.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import silicon.platform.Context;
import silicon.platform.result.CommandResult;
import silicon.transactions.holds.v1.service.command.LiquidateHoldIncatCommand;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class LiquidateHoldIncatServiceTest {

    @InjectMocks
    private LiquidateHoldIncatService liquidateHoldIncatService;

    @Mock
    private EndpointAdaptor endpointAdaptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testExecute_Success() {
        // Arrange
        LiquidateHoldIncatCommand command = new LiquidateHoldIncatCommand("12345");
        Context context = new Context();
        CommandResult<Void> commandResult = new CommandResult<>();

        when(endpointAdaptor.exchangeCommand(any(), any())).thenReturn(commandResult);

        // Act
        CommandResult<Void> result = liquidateHoldIncatService.execute(command, context);

        // Assert
        assertNotNull(result);
        verify(endpointAdaptor, times(1)).exchangeCommand(any(), any());
    }

    @Test
    void testExecute_Failure() {
        // Arrange
        LiquidateHoldIncatCommand command = new LiquidateHoldIncatCommand("12345");
        Context context = new Context();
        CommandResult<Void> failureResult = new CommandResult<>();
        failureResult.setFailure(true);

        when(endpointAdaptor.exchangeCommand(any(), any())).thenReturn(failureResult);

        // Act
        CommandResult<Void> result = liquidateHoldIncatService.execute(command, context);

        // Assert
        assertNotNull(result);
        verify(endpointAdaptor, times(1)).exchangeCommand(any(), any());
    }
}
