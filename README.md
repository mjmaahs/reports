import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

class GenerateTanfeethExcelCommandTest {

    @Test
    void testEqualsAndHashCode() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("key1", "value1");

        GenerateTanfeethExcelCommand cmd1 = new GenerateTanfeethExcelCommand("report1", new byte[]{1, 2, 3}, payload);
        GenerateTanfeethExcelCommand cmd2 = new GenerateTanfeethExcelCommand("report1", new byte[]{1, 2, 3}, payload);
        GenerateTanfeethExcelCommand cmd3 = new GenerateTanfeethExcelCommand("report2", new byte[]{4, 5, 6}, null);

        assertEquals(cmd1, cmd2);
        assertNotEquals(cmd1, cmd3);
        assertEquals(cmd1.hashCode(), cmd2.hashCode());
        assertNotEquals(cmd1.hashCode(), cmd3.hashCode());
    }

    @Test
    void testToString() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("key1", "value1");

        GenerateTanfeethExcelCommand cmd = new GenerateTanfeethExcelCommand("report1", null, payload);
        String expected = "GenerateTanfeethExcelCommand{reportName='report1', templateData=null, payload={key1=value1}}";
        assertEquals(expected, cmd.toString());
    }

    @Test
    void testConstructorAndArguments() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("key1", "value1");

        Exception exception = assertThrows(NullPointerException.class, () -> {
            new GenerateTanfeethExcelCommand(null, new byte[]{1, 2, 3}, payload);
        });

        assertTrue(exception.getMessage().contains("reportName cannot be null"));
    }
}
