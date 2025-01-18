/**
 * BSTDictionary.java
 * Ali Ajwani
 * 251374819
 * 
 * This class represents a dictionary implemented using a binary search tree.
 */
public class BSTDictionary implements BSTDictionaryADT {
    private BinarySearchTree bst;

    /**
     * This function initializes a new BSTDictionary with an empty binary search tree.
     */
    public BSTDictionary() {
        bst = new BinarySearchTree();
    }

    /**
     * This function retrieves the record associated with the specified key.
     *
     * @param k the key of the record to retrieve
     * @return the record associated with the key, or null if the key is not found
     */
    @Override
    public Record get(Key k) {
        BSTNode node = bst.get(bst.getRoot(), k);
        return node == null ? null : node.getRecord();
    }

    /**
     * This function inserts a new record into the dictionary.
     *
     * @param d the record to insert
     * @throws DictionaryException if the record already exists in the dictionary
     */
    @Override
    public void put(Record d) throws DictionaryException {
        bst.insert(bst.getRoot(), d);
    }

    /**
     * This function removes the record associated with the specified key from the dictionary.
     *
     * @param k the key of the record to remove
     * @throws DictionaryException if the key is not found in the dictionary
     */
    @Override
    public void remove(Key k) throws DictionaryException {
        bst.remove(bst.getRoot(), k);
    }

    /**
     * This function finds the successor of the specified key.
     *
     * @param k the key for which to find the successor
     * @return the record associated with the successor key, or null if there is no successor
     */
    @Override
    public Record successor(Key k) {
        BSTNode node = bst.successor(bst.getRoot(), k);
        if (node == null) {
            return null;
        } else {
            return node.getRecord();
        }
        
    }

    /**
     * This function finds the predecessor of the specified key.
     *
     * @param k the key for which to find the predecessor
     * @return the record associated with the predecessor key, or null if there is no predecessor
     */
    @Override
    public Record predecessor(Key k) {
        BSTNode node = bst.predecessor(bst.getRoot(), k);
        if (node == null) {
            return null;
        } else {
            return node.getRecord();
        }
        
    }

    /**
     * This function finds the smallest record in the dictionary.
     *
     * @return the smallest record, or null if the dictionary is empty
     */
    @Override
    public Record smallest() {
        BSTNode node = bst.smallest(bst.getRoot());
        if (node == null) {
            return null;
        } else {
            return node.getRecord();
        }
        
    }

    /**
     * This function finds the largest record in the dictionary.
     *
     * @return the largest record, or null if the dictionary is empty
     */
    @Override
    public Record largest() {
        BSTNode node = bst.largest(bst.getRoot());
        if (node == null) {
            return null;
        } else {
            return node.getRecord();
        }
        
    }
}
