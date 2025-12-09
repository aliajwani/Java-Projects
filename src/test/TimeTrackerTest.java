package my_package;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

/**
 * JUnit 5 test class for TimeTracker.
 * Tests methods in the TimeTracker class, including time calculations and file I/O.
 */
class TimeTrackerTest {

    private static final String TIME_JSON_PATH = "src/my_package/assets/data/Time.json";
    private TimeTracker timeTracker;

    /**
     * Set up method to create a TimeTracker instance before each test.
     */
    @BeforeEach
    void setUp() {
        // Setup a TimeTracker with a specific duration
        timeTracker = new TimeTracker(3600); // 1 hour in seconds
    }

    /**
     * Tests the averageTime method of the TimeTracker class.
     * It checks whether the average time is calculated correctly.
     */
    @Test
    void testAverageTime() {
        // We will need to mock the JSON file content for testing purposes
        try {
            String jsonData = "[{\"duration\": 3600}, {\"duration\": 7200}]"; // Sample durations in seconds
            Files.write(Paths.get(TIME_JSON_PATH), jsonData.getBytes());

            // Test average time calculation
            String averageTime = timeTracker.averageTime();
            assertNotNull(averageTime, "The average time should be calculated.");
            assertTrue(averageTime.contains("hours"), "Average time string should contain hours.");
        } catch (IOException e) {
            e.printStackTrace();
            fail("File write error during testing");
        }
    }

    /**
     * Tests the totalTime method of the TimeTracker class.
     * It checks whether the total time is calculated correctly.
     */
    @Test
    void testTotalTime() {
        // Test total time calculation with mock data
        try {
            String jsonData = "[{\"duration\": 3600}, {\"duration\": 7200}]"; // Sample durations in seconds
            Files.write(Paths.get(TIME_JSON_PATH), jsonData.getBytes());

            // Test total time calculation
            String totalTime = timeTracker.totalTime();
            assertNotNull(totalTime, "The total time should be calculated.");
            assertTrue(totalTime.contains("hours"), "Total time string should contain hours.");
        } catch (IOException e) {
            e.printStackTrace();
            fail("File write error during testing");
        }
    }

    /**
     * Tests the writeTime method of the TimeTracker class.
     * Verifies that the current duration is written to the JSON file.
     */
    @Test
    void testWriteTime() {
        try {
            // Initial state: no data in the file
            Files.deleteIfExists(Paths.get(TIME_JSON_PATH));

            // Write the current time to the file
            timeTracker.writeTime();

            // Check if the file now contains the expected data
            List<Map<String, Long>> durationList = new ObjectMapper().readValue(new File(TIME_JSON_PATH),
                    new TypeReference<List<Map<String, Long>>>() {});
            assertFalse(durationList.isEmpty(), "The time entry should have been written to the file.");
            assertEquals(1, durationList.size(), "There should be one entry in the JSON file.");
        } catch (IOException e) {
            e.printStackTrace();
            fail("File write error during testing");
        }
    }

    /**
     * Tests the toString method of the TimeTracker class.
     * It checks that the time is formatted correctly.
     */
    @Test
    void testToString() {
        String expectedFormat = "0 days, 01 hours, 00 minutes, 00 seconds";
        assertEquals(expectedFormat, timeTracker.toString(), "The time should be formatted correctly.");
    }
}
