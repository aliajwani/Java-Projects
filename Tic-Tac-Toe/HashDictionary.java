/**
 * HashDictionary.java
 * Ali Ajwani
 * 251374819 - aajwani2
 * October 27, 2024
 */

import java.util.LinkedList;

/**
 * This class implements a hash table with separate chaining using an array of linked lists.
 * It stores game configurations and provides functionality to add, retrieve, and remove configurations.
 */
public class HashDictionary implements DictionaryADT {
    private LinkedList<Data>[] table; // An array of linked lists to store the hash table
    private int size; // The size of the hash table
    private int numRecords; // The number of records currently in the hash table
    private static final double LOAD_FACTOR_THRESHOLD = 0.75; // The load factor threshold for resizing
    private static final int PRIME_NUMBER = 39; // The prime number used in the hash function

    /**
     * This function constructs a HashDictionary with an initial size that is a prime number greater than or equal to the specified initial size.
     *
     * @param initialSize the initial size of the hash table
     */
    @SuppressWarnings("unchecked")
    public HashDictionary(int initialSize) {
        int possibleSize = initialSize;

        // Find the next prime number greater than or equal to initialSize
        while (true) {
            boolean isPrime = true;
            if (possibleSize <= 1) {
                isPrime = false;
            } else {
                for (int i = 2; i * i <= possibleSize; i++) {
                    if (possibleSize % i == 0) {
                        isPrime = false;
                        break;
                    }
                }
            }
            if (isPrime) {
                this.size = possibleSize;
                break;
            }
            possibleSize++;
        }

        this.table = new LinkedList[this.size];
        for (int i = 0; i < this.size; i++) {
            table[i] = new LinkedList<>();
        }

        this.numRecords = 0;
    }

    /**
     * This function computes the hash code for a given configuration string.
     *
     * @param config the configuration string to hash
     * @return the computed hash code (index for the table)
     */
    private int hashFunction(String config) {
        int hash = 0;
        int i = 0;
        while (i < config.length()) {
            hash = (PRIME_NUMBER * hash + config.charAt(i)) % size;
            i++;
        }
        return hash;
    }

    /**
     * This function adds a record to the hash table. Throws a DictionaryException if a record with the same configuration already exists.
     * Resizes the hash table if the load factor exceeds the threshold.
     *
     * @param record the record to add
     * @return 1 if a collision occurred, 0 otherwise
     * @throws DictionaryException if a record with the same configuration exists
     */
    @Override
    public int put(Data record) {
        if (numRecords >= size * LOAD_FACTOR_THRESHOLD) {
            resize();
        }
        int index = hashFunction(record.getConfiguration());
    
        // Check for existing record with the same configuration
        int i = 0;
        while (i < table[index].size()) {
            Data data = table[index].get(i);
            if (data.getConfiguration().equals(record.getConfiguration())) {
                throw new DictionaryException();
            }
            i++;
        }
    
        table[index].add(record);
        numRecords++;
        return table[index].size() > 1 ? 1 : 0;
    }

    /**
     * This function resizes the hash table when the load factor threshold is exceeded.
     * Finds the next prime size for the resized table, rehashing all existing records.
     */
    @SuppressWarnings("unchecked")
    private void resize() {
        int newSize = size * 2;

        // Find the next prime number greater than or equal to size * 2
        while (true) {
            boolean isPrime = true;
            if (newSize <= 1) {
                isPrime = false;
            } else {
                for (int i = 2; i * i <= newSize; i++) {
                    if (newSize % i == 0) {
                        isPrime = false;
                        break;
                    }
                }
            }
            if (isPrime) {
                break;
            }
            newSize++;
        }

        // Initialize a new table with the calculated prime size
        LinkedList<Data>[] newTable = new LinkedList[newSize];
        for (int i = 0; i < newSize; i++) {
            newTable[i] = new LinkedList<>();
        }

        // Rehash all existing records into the new table
        for (int i = 0; i < size; i++) {
            for (Data data : table[i]) {
                int newIndex = hashFunction(data.getConfiguration());
                newTable[newIndex].add(data);
            }
        }

        this.size = newSize;
        this.table = newTable;
    }

    /**
     * This function removes the record with the given configuration from the hash table.
     *
     * @param config the configuration to remove
     * @throws DictionaryException if no record with the given configuration is found
     */
    @Override
    public void remove(String config) {
        int index = hashFunction(config);
        for (Data data : table[index]) {
            if (data.getConfiguration().equals(config)) {
                table[index].remove(data);
                numRecords--;
                return;
            }
        }
        throw new DictionaryException();
    }

    /**
     * This function retrieves the score associated with the given configuration.
     *
     * @param config the configuration to look up
     * @return the score if the configuration is found, -1 otherwise
     */
    @Override
    public int get(String config) {
        int index = hashFunction(config);
        int i = 0;
        while (i < table[index].size()) {
            Data data = table[index].get(i);
            if (data.getConfiguration().equals(config)) {
                return data.getScore();
            }
            i++;
        }
        return -1;
    }
    

    /**
     * This function returns the number of records in the hash table.
     *
     * @return the number of records in the hash table
     */
    @Override
    public int numRecords() {
        return numRecords;
    }
}
