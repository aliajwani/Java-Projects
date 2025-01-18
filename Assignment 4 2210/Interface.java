import java.io.*;
import java.util.StringTokenizer;

/**
 * Interface.java
 * Ali Ajwani
 * 251374819
 * 
 * This class implements a user interface for managing an ordered dictionary of records.
 * The commands allow users to define, translate, play media, display images and webpages,
 * add or delete records, and list or retrieve the first and last entries in the dictionary.
 */
public class Interface {
    private static BSTDictionary dictionary = new BSTDictionary();

    /**
     * This function is the main entry point of the program, which loads records and processes user commands.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java Interface <inputFile>");
            return;
        }

        try {
            loadRecords(args[0]);
            runUserInterface();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
 * This function loads records from the specified file into the dictionary.
 *
 * @param fileName the name of the input file
 * @throws IOException if an I/O error occurs
 * @throws DictionaryException if a duplicate record is found
 */
private static void loadRecords(String fileName) throws IOException, DictionaryException {
    BufferedReader reader = new BufferedReader(new FileReader(fileName)); // Open the file for reading
    String label;

    // Read records line by line until the end of the file is reached
    while ((label = reader.readLine()) != null) {
        String dataLine = reader.readLine(); // Read the data associated with the label
        if (dataLine == null) break; // Exit loop if data line is missing

        int type = determineType(dataLine); 

        // Check if the data type is translation and adjust data accordingly
        if (type == 2) {
            dataLine = dataLine.substring(1); 
        }

        // Create a Key object using the label and type
        Key key = new Key(label, type);

        // Create a Record object with the key and data line
        Record record = new Record(key, dataLine);

        // Insert the record into the dictionary
        dictionary.put(record);
    }
    reader.close(); // Close the file reader after processing
}


    /**
     * This function determines the data type based on file prefix or suffix.
     *
     * @param dataLine the data line to evaluate
     * @return an integer representing the type
     */
    private static int determineType(String dataLine) {
        if (dataLine.startsWith("/")) {
            return 2; // translation
        } else if (dataLine.startsWith("-")) {
            return 3; // sound
        } else if (dataLine.startsWith("+")) {
            return 4; // music
        } else if (dataLine.startsWith("*")) {
            return 5; // voice
        } else if (dataLine.endsWith(".jpg")) {
            return 6; // image
        } else if (dataLine.endsWith(".gif")) {
            return 7; // animated image
        } else if (dataLine.endsWith(".html")) {
            return 8; // webpage
        } else {
            return 1; // definition
        }        
    }

    /**
     * This function runs a command-line interface to process user commands.
     */
    private static void runUserInterface() {
        StringReader board = new StringReader();
        String input;

        while (true) {
            input = board.read("Enter next command: ");
            if (input == null || input.isEmpty()) continue;

            if (input.equals("exit")) break;

            StringTokenizer tokenizer = new StringTokenizer(input);

            String command = tokenizer.nextToken();

            try {
                switch (command) {
                    case "define": fetchData(tokenizer, 1, "The word %s is not in the dictionary"); break;
                    case "translate": fetchData(tokenizer, 2, "There is no definition for the word %s"); break;
                    case "sound": playMedia(tokenizer, 3, "There is no sound file for %s"); break;
                    case "play": playMedia(tokenizer, 4, "There is no music file for %s"); break;
                    case "say": playMedia(tokenizer, 5, "There is no voice file for %s"); break;
                    case "show": displayMedia(tokenizer, 6, "There is no image file for %s"); break;
                    case "animate": displayMedia(tokenizer, 7, "There is no animated image file for %s"); break;
                    case "browse": displayWeb(tokenizer); break;
                    case "delete": deleteRecord(tokenizer); break;
                    case "add": addRecord(tokenizer); break;
                    case "list": listRecords(tokenizer); break;
                    case "first": displayFirstRecord(); break;
                    case "last": displayLastRecord(); break;
                    default: System.out.println("Invalid command."); break;
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    /**
     * This function fetches and displays the data associated with a specified label and type.
     *
     * @param tokenizer the tokenizer containing the command arguments
     * @param type the type of record to fetch
     * @param errorMessage the error message if the record is not found
     */
    private static void fetchData(StringTokenizer tokenizer, int type, String errorMessage) {
        // Check if additional arguments are provided; if not, print an error message and exit
        if (!tokenizer.hasMoreTokens()) {
            System.out.println("Invalid command.");
            return;
        }
        // Retrieve the label from the tokenizer and create a key for searching in the dictionary
        String label = tokenizer.nextToken();
        Key key = new Key(label, type);
        // Get the record from the dictionary and print its data if it exists, otherwise print an error message
        Record record = dictionary.get(key);
        System.out.println(record != null ? record.getDataItem() : String.format(errorMessage, label));
    }
    
    /**
     * This function plays a media file (sound, music, or voice) if available.
     *
     * @param tokenizer the tokenizer containing the command arguments
     * @param type the media type to play
     * @param errorMessage the error message if the media file is not found
     */
    private static void playMedia(StringTokenizer tokenizer, int type, String errorMessage) {
        // Ensure the command includes a label, otherwise display an error message and exit
        if (!tokenizer.hasMoreTokens()) {
            System.out.println("Invalid command.");
            return;
        }
        // Retrieve the label from the tokenizer and convert it to lowercase to create a key for the dictionary
        String label = tokenizer.nextToken();
        Key key = new Key(label.toLowerCase(), type);
        // Fetch the record; if found, attempt to play the file, otherwise print an error message
        Record record = dictionary.get(key);
        if (record != null) {
            try {
                new SoundPlayer().play(record.getDataItem());
            } catch (Exception e) {
                System.out.println("Error playing file: " + e.getMessage());
            }
        } else {
            System.out.println(String.format(errorMessage, label));
        }
    }
    
    /**
     * This function displays an image file if available.
     *
     * @param tokenizer the tokenizer containing the command arguments
     * @param type the image type to display
     * @param errorMessage the error message if the image file is not found
     */
    private static void displayMedia(StringTokenizer tokenizer, int type, String errorMessage) {
        // Check if the command includes a label; otherwise, show an error message and exit
        if (!tokenizer.hasMoreTokens()) {
            System.out.println("Invalid command.");
            return;
        }
        // Create a key using the label and type, then attempt to fetch the record from the dictionary
        String label = tokenizer.nextToken();
        Key key = new Key(label.toLowerCase(), type);
        Record record = dictionary.get(key);
        // If the record exists, try to display the image, or else show an error message
        if (record != null) {
            try {
                new PictureViewer().show(record.getDataItem());
            } catch (Exception e) {
                System.out.println("Error displaying image file: " + e.getMessage());
            }
        } else {
            System.out.println(String.format(errorMessage, label));
        }
    }
    
    /**
     * This function displays a webpage if available.
     *
     * @param tokenizer the tokenizer containing the command arguments
     */
    private static void displayWeb(StringTokenizer tokenizer) {
        // Ensure a label is provided in the command; if not, show an error message
        if (!tokenizer.hasMoreTokens()) {
            System.out.println("Invalid command.");
            return;
        }
        // Create a key for the webpage and attempt to retrieve it from the dictionary
        String label = tokenizer.nextToken();
        Key key = new Key(label, 8);
        Record record = dictionary.get(key);
        // If the webpage record exists, display it; otherwise, print an error message
        if (record != null) {
            try {
                new ShowHTML().show(record.getDataItem());
            } catch (Exception e) {
                System.out.println("Error displaying webpage: " + e.getMessage());
            }
        } else {
            System.out.println("There is no webpage called " + label);
        }
    }
    
    /**
     * This function deletes a record from the dictionary if it exists.
     *
     * @param tokenizer the tokenizer containing the command arguments
     */
    private static void deleteRecord(StringTokenizer tokenizer) {
        // Verify that a label and type are provided; otherwise, print an error message
        if (!tokenizer.hasMoreTokens()) {
            System.out.println("Invalid command.");
            return;
        }
        // Retrieve the label and type, then create a key for the dictionary search
        String label = tokenizer.nextToken();
        int type = Integer.parseInt(tokenizer.nextToken());
        Key key = new Key(label, type);
        // Attempt to remove the record; if it doesn't exist, display an error message
        try {
            dictionary.remove(key);
        } catch (DictionaryException e) {
            System.out.println("No record in the ordered dictionary has key (" + label + "," + type + ")");
        }
    }
    
    /**
     * This function adds a new record to the dictionary if it doesn't already exist.
     *
     * @param tokenizer the tokenizer containing the command arguments
     */
    private static void addRecord(StringTokenizer tokenizer) {
        // Check if label and type are included in the command; if not, show an error message
        if (!tokenizer.hasMoreTokens()) {
            System.out.println("Invalid command.");
            return;
        }
        // Retrieve the label and type and concatenate any remaining tokens as the data
        String label = tokenizer.nextToken();
        int type = Integer.parseInt(tokenizer.nextToken());
        StringBuilder dataBuilder = new StringBuilder();
        while (tokenizer.hasMoreTokens()) {
            dataBuilder.append(tokenizer.nextToken()).append(" ");
        }
        String data = dataBuilder.toString().trim();
        // Create a key and record, and attempt to add it to the dictionary
        Key key = new Key(label.toLowerCase(), type);
        Record record = new Record(key, data);
        try {
            dictionary.put(record);
        } catch (DictionaryException e) {
            System.out.println("A record with the given key (" + label + "," + type + ") is already in the ordered dictionary");
        }
    }
    
    /**
     * This function lists records with labels starting with the given prefix.
     *
     * @param tokenizer the tokenizer containing the command arguments
     */
    private static void listRecords(StringTokenizer tokenizer) {
        // Verify that a prefix is provided; if not, display an error
        if (!tokenizer.hasMoreTokens()) {
            System.out.println("Invalid command.");
            return;
        }
        
        String prefix = tokenizer.nextToken().toLowerCase();
        Record current = dictionary.smallest();
        boolean hasMatches = false;
    
        // Iterate through all records in the dictionary
        while (current != null) {
            Key key = current.getKey();
            String label = key.getLabel();
    
            // Check if the label matches the specified prefix and print it if it does
            if (label.startsWith(prefix)) {
                if (hasMatches) {
                    System.out.print(", "); // Adds a comma between entries if there are multiple matches
                }
                System.out.print(label);
                hasMatches = true;
            }
            
            // Move to the next record in the dictionary
            current = dictionary.successor(key);
        }
    
        // If no matches were found, print a message indicating this; otherwise, add a new line
        if (!hasMatches) {
            System.out.println("No label attributes in the ordered dictionary start with prefix " + prefix);
        } else {
            System.out.println(); // Ends the line after listing all matching labels
        }
    }
    
    /**
     * This function displays the smallest record in the dictionary.
     */
    private static void displayFirstRecord() {
        // Retrieve the smallest record and print its details if it exists
        Record record = dictionary.smallest();
        if (record != null) {
            Key key = record.getKey();
            System.out.println(key.getLabel() + "," + key.getType() + "," + record.getDataItem());
        } else {
            System.out.println("The dictionary is empty");
        }
    }
    
    /**
     * This function displays the largest record in the dictionary.
     */
    private static void displayLastRecord() {
        // Retrieve the largest record and print its details if it exists
        Record record = dictionary.largest();
        if (record != null) {
            Key key = record.getKey();
            System.out.println(key.getLabel() + "," + key.getType() + "," + record.getDataItem());
        } else {
            System.out.println("The dictionary is empty");
        }
    }
}