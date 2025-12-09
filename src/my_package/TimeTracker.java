package my_package;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a time tracking manager for the Virtual Pet Project.
 * This class is responsible for calculating and handling time data.
 * Specifically, for displaying the time spent on application.
 * 
 * @author JackFedash
 */
public class TimeTracker {
    private final long duration; // Duration in seconds

    /**
     * Constructor to initialize the TimeTracker with a specific duration.
     *
     * @param duration The time spent on the application in seconds.
     */
    public TimeTracker(long duration) {
        this.duration = duration; // Set the duration for the tracker
    }

    /**
     * Calculates the average time spent on the application based on JSON data and current duration.
     *
     * @return A string representation of the average time.
     */
    public String averageTime() {
        ObjectMapper objectMapper = new ObjectMapper();
        long average = this.duration; // Initialize average with current duration
        long counter = 1; // Initialize counter to include the current duration

        try {
            // Read the list of durations from the JSON file
            List<Map<String, Long>> durationEntries = objectMapper.readValue(new File("src/my_package/assets/data/Time.json"),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, Map.class));

            // Sum all durations from JSON entries
            for (Map<String, Long> entry : durationEntries) {
                Object durationObj = entry.get("duration"); // Retrieve the duration object
                average += ((Integer) durationObj).longValue(); // Convert Integer to Long and sum
                counter++; // Increment counter, amount of durations added to average
            }
        } catch (IOException e) {
            e.printStackTrace(); // Print error if unable to read file
        }
        
        average /= counter; // Calculate the average duration
        return (new TimeTracker(average)).toString(); // Return the average as a formatted string
    }

    /**
     * Calculates the total time spent on the application based on JSON data and current duration.
     *
     * @return A string representation of the total time in the format HH:MM:SS.
     */
    public String totalTime() {
        ObjectMapper objectMapper = new ObjectMapper();
        long total = this.duration; // Initialize total with current duration

        try {
            // Read the list of durations from the JSON file
            List<Map<String, Long>> durationEntries = objectMapper.readValue(new File("src/my_package/assets/data/Time.json"),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, Map.class));

            // Sum all durations from JSON entries
            for (Map<String, Long> entry : durationEntries) {
                Object durationObj = entry.get("duration"); // Retrieve the duration object
                total += ((Integer) durationObj).longValue(); // Summing durations
            }
        } catch (IOException e) {
            e.printStackTrace(); // Print error if unable to read file
        }
        
        return (new TimeTracker(total)).toString(); // Return the total as a formatted string
    }

    /**
     * Writes the current duration to the JSON file.
     * This creates or updates the access times in the Time.json file.
     * 
     */
    public void writeTime() {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File("src/my_package/assets/data/Time.json"); // File to store time data

        // Create a new entry for the duration
        Map<String, Long> newDurationEntry = new HashMap<>();
        newDurationEntry.put("duration", this.duration); // Add current duration to the entry

        List<Map<String, Long>> durationList;

        try {
            // Read existing durations from the JSON file
            durationList = mapper.readValue(file, new TypeReference<List<Map<String, Long>>>() {});
        } catch (IOException e) {
            e.printStackTrace(); // Print error if unable to read file
            durationList = new ArrayList<>(); // Initialize a new list if there's an error
        }

        // Append the new entry to the existing list
        durationList.add(newDurationEntry);

        // Write the updated list back to the JSON file
        try {
            mapper.writeValue(file, durationList); // Write the updated list to the JSON file
        } catch (IOException e) {
            e.printStackTrace(); // Print error if unable to write to file
        }
    }

    /**
     * Returns a formatted string representing the duration.
     * 
     * @return A string formatted as "X days, HH hours, MM minutes, SS seconds".
     */
    @Override
    public String toString() {
        long seconds = this.duration / 1000; // Convert duration from milliseconds to seconds
        long days = seconds / 86400; // Calculate the number of days
        long hours = (seconds % 86400) / 3600; // Calculate the remaining hours
        long minutes = (seconds % 3600) / 60; // Calculate the remaining minutes
        seconds = seconds % 60; // Calculate the remaining seconds

        // Return the formatted string
        return String.format("%d days, %02d hours, %02d minutes, %02d seconds", days, hours, minutes, seconds);
    }
}