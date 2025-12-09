package my_package;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

/**
 * Represents a playtime restrictions manager for the Virtual Pet Project.
 * This class handles access to the game based on specified time restrictions.
 * 
 * @author JackFedash
 */
public class AccessManager {
    /**
     * Writes a new access time to the JSON file based on user input.
     *
     * @param inputTime The input time string from the user Text box. 
     */
    public void writeTime(String inputTime) {
        // Setup pattern with regex to check if input matches the expected formatting
        Pattern pattern = Pattern.compile("^(\\d{1,2}):(\\d{1,2})-(\\d{1,2}):(\\d{1,2})$"); // Setup regex format format "HH:MM-HH:MM"
        Matcher matcher = pattern.matcher(inputTime); // Create matcher to check regex vs input
        
        // If it does not match, send an alert and end the function
        if (!matcher.matches()) {
            JOptionPane.showMessageDialog(null, "An incorrect format was entered, the access time will not be saved. Please follow the formatting rules specified below the text box.", "Wrong Format", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Parse integer values from the input string
        int startHour = Integer.parseInt(matcher.group(1)); // First hour
        int startMin = Integer.parseInt(matcher.group(2)); // First minute
        int endHour = Integer.parseInt(matcher.group(3)); // Second hour
        int endMin = Integer.parseInt(matcher.group(4)); // Second minute

        // Check if parsed values represent valid time. If not, show an alert
        if (!(startHour >= 0 && startHour <= 23 && startMin >= 0 && startMin <= 59 &&
              endHour >= 0 && endHour <= 23 && endMin >= 0 && endMin <= 59)) {
            JOptionPane.showMessageDialog(null, "An incorrect time value was entered. Enter a real time value.", "Invalid Number", JOptionPane.INFORMATION_MESSAGE);
            return; // Exit method if input values are invalid
        }

        // If valid input, write to file to store restriction times
        ObjectMapper mapper = new ObjectMapper();
        File file = new File("src/my_package/assets/data/AccessTimes.json");

        // Create a new entry for the access time
        Map<String, Integer> newAccessTimeEntry = new HashMap<>();
        newAccessTimeEntry.put("startHour", startHour);
        newAccessTimeEntry.put("startMin", startMin);
        newAccessTimeEntry.put("endHour", endHour);
        newAccessTimeEntry.put("endMin", endMin);

        List<Map<String, Integer>> accessTimeList;

        try {
            // Read existing access durations from the file
            accessTimeList = mapper.readValue(file, new TypeReference<List<Map<String, Integer>>>() {});
        } catch (IOException e) {
            accessTimeList = new ArrayList<>(); // Initialize a new list if there's an error reading existing data. (File was cleared)
        }

        // Append the new access time entry to the list
        accessTimeList.add(newAccessTimeEntry);

        // Write the updated list back to the JSON file
        try {
            mapper.writeValue(file, accessTimeList); // Write the complete updated list back to the file
            JOptionPane.showMessageDialog(null, "Successfully added the playtime limit.", "Added Playtime Limit", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace(); // Print error if unable to write to the file
        }
    }

    /**
     * Checks if the user is able to access newGame and loadGame at the current time.
     *
     * @return true if access is granted, false otherwise.
     */
    public boolean canAccessGame() {
        File file = new File("src/my_package/assets/data/AccessTimes.json"); // Create a File object pointing to the access time JSON

        // Check the length of the file
        if (file.length() == 0) return true; // Returns true if the file is empty, allowing access. If there are not restrictions, allow access.

        ObjectMapper objectMapper = new ObjectMapper();
        boolean canAccess = false; // Initialize access status

        try {
            // Read the list of access duration entries from the JSON file
            List<Map<String, Long>> durationEntries = objectMapper.readValue(file, 
                    objectMapper.getTypeFactory().constructCollectionType(List.class, Map.class));

            // For all limits check if access is granted
            for (Map<String, Long> entry : durationEntries) {
                Object startHour = entry.get("startHour"); // Retrieve the start hour
                Object startMin = entry.get("startMin"); // Retrieve the start minute
                Object endHour = entry.get("endHour"); // Retrieve the end hour
                Object endMin = entry.get("endMin"); // Retrieve the end minute

                // Check if the current time falls within the allowed access range
                if (checkAccess((Integer) startHour, (Integer) startMin, (Integer) endHour, (Integer) endMin)) {
                    canAccess = true; // Update access status if within the access time
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // Print stack trace if an exception occurs
        }
        return canAccess; // Return access status
    }
    
    /**
     * Checks if the current time is within the provided access time limits.
     *
     * @param startHour The hour when access starts.
     * @param startMin The minute when access starts.
     * @param endHour The hour when access ends.
     * @param endMin The minute when access ends.
     * @return true if the current time is within access hours; false otherwise.
     */
    private boolean checkAccess(int startHour, int startMin, int endHour, int endMin) {
        LocalTime currentTime = LocalTime.now(); // Get the current time

        // Define the allowed access times based on parameters
        LocalTime startAccessTime = LocalTime.of(startHour, startMin); // Create the start access time
        LocalTime endAccessTime = LocalTime.of(endHour, endMin); // Create the end access time

        // Check if current time is within the defined range
        // Case when access time is on the same day
        if (startAccessTime.isBefore(endAccessTime)) {
            return !currentTime.isBefore(startAccessTime) && !currentTime.isAfter(endAccessTime);
        } else {
            // Case when access time crosses midnight. Ex. 11pm-5am.
            return !currentTime.isBefore(startAccessTime) || !currentTime.isAfter(endAccessTime);
        }
    }
}