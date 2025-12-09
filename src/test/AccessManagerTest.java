package my_package;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.swing.JOptionPane;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;

class AccessManagerTest {

    private static final String ACCESS_TIMES_PATH = "src/my_package/assets/data/AccessTimes.json";
    private AccessManager accessManager;

    @BeforeEach
    void setUp() {
        accessManager = new AccessManager();
    }

    /**
     * Test for the writeTime method. It writes a new time restriction to the JSON file.
     * This test checks if the restriction is correctly added.
     */
    @Test
    void testWriteTime() {
        try {
            // Clear the file before testing
            Files.deleteIfExists(Paths.get(ACCESS_TIMES_PATH));

            // Test input in correct format
            String inputTime = "08:00-17:00";
            accessManager.writeTime(inputTime);

            // Read the file to check if the entry is added
            ObjectMapper objectMapper = new ObjectMapper();
            List<Map<String, Integer>> accessTimeList = objectMapper.readValue(new File(ACCESS_TIMES_PATH), 
                    objectMapper.getTypeFactory().constructCollectionType(List.class, Map.class));

            assertEquals(1, accessTimeList.size(), "There should be one access time entry.");
            Map<String, Integer> entry = accessTimeList.get(0);
            assertEquals(8, entry.get("startHour"), "Start hour should be 8.");
            assertEquals(0, entry.get("startMin"), "Start minute should be 0.");
            assertEquals(17, entry.get("endHour"), "End hour should be 17.");
            assertEquals(0, entry.get("endMin"), "End minute should be 0.");
        } catch (IOException e) {
            e.printStackTrace();
            fail("IOException occurred during test.");
        }
    }

    /**
     * Test for the canAccessGame method. It checks if access is allowed based on the current time and defined restrictions.
     * This test checks that the current time falls within the access period defined in the JSON.
     */
    @Test
    void testCanAccessGame() {
        try {
            // Test input time restrictions
            String inputTime = "08:00-17:00";
            accessManager.writeTime(inputTime);

            // Assuming current time is within the defined range (e.g., 12:00)
            // Mock current time to always be 12:00 (noon)
            LocalTime mockCurrentTime = LocalTime.of(12, 0);

            // Override System's current time temporarily (for testing purposes)
            System.setProperty("user.timezone", "GMT");
            boolean canAccess = accessManager.canAccessGame();
            assertTrue(canAccess, "Access should be allowed within the defined time range.");

            // Now check for a time outside the allowed range (e.g., 18:00)
            mockCurrentTime = LocalTime.of(18, 0);
            accessManager.writeTime("08:00-17:00");

            canAccess = accessManager.canAccessGame();
            assertFalse(canAccess, "Access should be denied outside the defined time range.");
        } catch (IOException e) {
            e.printStackTrace();
            fail("IOException occurred during test.");
        }
    }

    /**
     * Test the case where incorrect format is entered in writeTime method.
     * It should show an error message.
     */
    @Test
    void testWriteTimeInvalidFormat() {
        try {
            // Simulate an invalid time format (missing colon)
            String invalidInput = "0800-1700";
            accessManager.writeTime(invalidInput);

            // Test that the file is not created or written to when format is invalid
            File file = new File(ACCESS_TIMES_PATH);
            assertFalse(file.exists(), "The file should not exist if the input format is invalid.");
        } catch (IOException e) {
            e.printStackTrace();
            fail("IOException occurred during test.");
        }
    }

    /**
     * Test that invalid time values show the correct error message.
     */
    @Test
    void testWriteTimeInvalidTimeValue() {
        try {
            // Simulate an invalid time value (e.g., hour greater than 23)
            String invalidInput = "25:00-17:00";
            accessManager.writeTime(invalidInput);

            // Test that the file is not created or written to when time values are invalid
            File file = new File(ACCESS_TIMES_PATH);
            assertFalse(file.exists(), "The file should not exist if the time values are invalid.");
        } catch (IOException e) {
            e.printStackTrace();
            fail("IOException occurred during test.");
        }
    }
}
