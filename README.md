package silicon.watheeqinbound.updateamount.service.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import silicon.platform.Context;
import silicon.platform.contract.CommandResult;
import silicon.platform.contract.Type;
import silicon.watheeqinbound.utils.WatheeqResponseHandler;
import silicon.watheeqinbound.liftrestrictions.service.banktransfer.domestic.data.dto.TicketDetailsQuery;
import silicon.watheeqinbound.liftrestrictions.service.banktransfer.domestic.data.dto.TicketDetailsResponse;
import silicon.watheeqinbound.liftrestrictions.service.banktransfer.domestic.service.TicketInterService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class UpdateAmountServiceTest {

    @Mock
    private WatheeqResponseHandler responseHandler;

    @Mock
    private EventHandler eventHandler;

    @Mock
    private TicketInterService overrideTicketInterService;

    @Mock
    private TicketInterService liftTicketInterService;

    @Mock
    private TicketInterService getTicketInterService;

    @InjectMocks
    private UpdateAmountService updateAmountService;

    private Context context;
    private UpdateAmountCommand command;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        context = new Context();
        command = new UpdateAmountCommand();
    }

    @Test
    public void testExecuteWithReverseAction() {
        command.setBlockStatus(BlockStatus.REVERSE_ACTION);
        var ticketList = new CommandResult<>(List.of(new TicketDetailsResponse()), Type.OK);
        when(getTicketInterService.decorate(any(), any())).thenReturn(ticketList);

        CommandResult<Void> result = updateAmountService.execute(command, context);

        assertNotNull(result);
        assertTrue(result.getData().isPresent());
        verify(getTicketInterService, times(1)).decorate(any(), any());
        verify(responseHandler, times(1)).createDataSuccessCommandResponse(any(), any());
    }

    @Test
    public void testExecuteWithOverrideAction() {
        command.setBlockStatus(BlockStatus.OVERRIDE_ACTION);
        var ticketList = new CommandResult<>(List.of(new TicketDetailsResponse()), Type.OK);
        when(getTicketInterService.decorate(any(), any())).thenReturn(ticketList);

        CommandResult<Void> result = updateAmountService.execute(command, context);

        assertNotNull(result);
        assertTrue(result.getData().isPresent());
        verify(getTicketInterService, times(1)).decorate(any(), any());
        verify(responseHandler, times(1)).createDataSuccessCommandResponse(any(), any());
    }

    @Test
    public void testExecuteWithInvalidStatus() {
        command.setBlockStatus(BlockStatus.INVALID);
        var ticketList = new CommandResult<>(List.of(new TicketDetailsResponse()), Type.OK);
        when(getTicketInterService.decorate(any(), any())).thenReturn(ticketList);

        CommandResult<Void> result = updateAmountService.execute(command, context);

        assertNotNull(result);
        assertTrue(result.getData().isPresent());
        verify(getTicketInterService, times(1)).decorate(any(), any());
        verify(responseHandler, times(1)).createDataSuccessCommandResponse(any(), any());
    }

    @Test
    public void testExecuteWithValidStatus() {
        command.setBlockStatus(BlockStatus.VALID);
        var ticketList = new CommandResult<>(List.of(new TicketDetailsResponse()), Type.OK);
        when(getTicketInterService.decorate(any(), any())).thenReturn(ticketList);

        CommandResult<Void> result = updateAmountService.execute(command, context);

        assertNotNull(result);
        assertTrue(result.getData().isPresent());
        verify(getTicketInterService, times(1)).decorate(any(), any());
        verify(responseHandler, times(1)).createDataSuccessCommandResponse(any(), any());
    }
}
