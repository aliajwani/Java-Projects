/**
 * Key.java
 * Ali Ajwani
 * 
 * This class represents the key of data items stored in internal nodes of the binary search tree.
 */
public class Key implements Comparable<Key> {
    private String label;
    private int type;

    /**
     * This function initializes a new Key object with the specified parameters.
     * The label is converted to lowercase before being stored.
     *
     * @param theLabel the label of the key
     * @param theType the type associated with the key
     */
    public Key(String theLabel, int theType) {
        this.label = theLabel.toLowerCase();
        this.type = theType;
    }

    /**
     * This function returns the label stored in the key.
     *
     * @return the label of this key
     */
    public String getLabel() {
        return label;
    }

    /**
     * This function returns the type stored in the key.
     *
     * @return the type of this key
     */
    public int getType() {
        return type;
    }

    /**
     * This function compares this Key object with another Key object.
     *
     * @param k the Key object to compare with
     * @return 0 if this Key object is equal to the specified Key object,
     * @return -1 if this Key object is smaller 
     * @return 1 if this Key object is larger
     */
    @Override
    public int compareTo(Key k) {
        if (this.label.equals(k.label)) {
            return Integer.compare(this.type, k.type);
        }
        return this.label.compareTo(k.label);
    }
}
