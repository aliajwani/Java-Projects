/**
 * Record.java
 * Ali Ajwani
 * 
 * This class represents a record to be stored in the internal nodes of the binary search tree.
 */
public class Record {
    private Key theKey;
    private String data;

    /**
     * This function initializes a new Record object with the specified key and data.
     *
     * @param k the key associated with this record
     * @param theData the data associated with this record
     */
    public Record(Key k, String theData) {
        this.theKey = k;
        this.data = theData;
    }

    /**
     * This function returns the key of the record.
     *
     * @return the key associated with this record
     */
    public Key getKey() {
        return theKey;
    }

    /**
     * This function returns the data associated with the key.
     *
     * @return the data associated with the key
     */
    public String getDataItem() {
        return data;
    }
}
