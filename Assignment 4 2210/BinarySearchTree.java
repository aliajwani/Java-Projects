/**
 * BinarySearchTree.java
 * Ali Ajwani 
 * 251374819
 * 
 * This class represents a binary search tree that stores records with unique keys.
 */
public class BinarySearchTree {
    private BSTNode root; // represents a binary search tree root

    /**
     * This function initializes an empty binary search tree.
     */
    public BinarySearchTree() {
        root = null; // Initialize root as null to signify an empty tree
    }

    /**
     * This function returns the root node of the binary search tree.
     *
     * @return the root node of the binary search tree
     */
    public BSTNode getRoot() {
        return root;
    }

    /**
     * This function searches for a node with the specified key in the subtree rooted at the given node.
     *
     * @param r the root of the subtree to search
     * @param k the key to search for
     * @return the node with the specified key, or null if not found
     */
    public BSTNode get(BSTNode r, Key k) {
        if (r == null) {
            return null; // Return null if node does not exist
        }
        
        int comparison = k.compareTo(r.getRecord().getKey()); // Compare the keys
        
        if (comparison < 0) {
            return get(r.getLeftChild(), k); // Search left if key is smaller
        }
        
        if (comparison > 0) {
            return get(r.getRightChild(), k); // Search right if key is larger
        }
        
        return r; // Node found with matching key
    }

    /**
     * This function inserts a record into the binary search tree.
     *
     * @param r the root of the subtree where the record should be inserted
     * @param d the record to insert
     * @throws DictionaryException if a duplicate key is found
     */
    public void insert(BSTNode r, Record d) throws DictionaryException {
        if (root == null) {
            root = new BSTNode(d); // Set root if tree is empty
            return;
        }
        
        int comparison = d.getKey().compareTo(r.getRecord().getKey());
        
        if (comparison < 0) {
            if (r.getLeftChild() != null) {
                insert(r.getLeftChild(), d); // Recurse left if child exists
                return;
            }
            BSTNode newNode = new BSTNode(d); // Create new node on the left
            r.setLeftChild(newNode);
            newNode.setParent(r);
            return;
        }
        
        if (comparison > 0) {
            if (r.getRightChild() != null) {
                insert(r.getRightChild(), d); // Recurse right if child exists
                return;
            }
            BSTNode newNode = new BSTNode(d); // Create new node on the right
            r.setRightChild(newNode);
            newNode.setParent(r);
            return;
        }
        
        throw new DictionaryException("Duplicate key"); // Key already exists
    }

    /**
     * This function removes a node with the specified key from the binary search tree.
     *
     * @param r the root of the subtree to search for the node to remove
     * @param k the key of the node to remove
     * @throws DictionaryException if the key is not found
     */
    public void remove(BSTNode r, Key k) throws DictionaryException {
        BSTNode node = get(r, k); // Find node to remove
        if (node == null) {
            throw new DictionaryException("Key not found"); // Node does not exist
        }

        if (node.isLeaf()) { // Node has no children
            if (node == root) {
                root = null; // Tree is now empty
            } else {
                BSTNode par = node.getParent();
                if (par.getLeftChild() == node) {
                    par.setLeftChild(null); // Remove as left child
                } else {
                    par.setRightChild(null); // Remove as right child
                }
            }
            return;
        }
        
        if (node.getLeftChild() != null && node.getRightChild() != null) {
            BSTNode successor = smallest(node.getRightChild()); // Find smallest node in right subtree
            node.setRecord(successor.getRecord()); // Copy successor's data
            remove(successor, successor.getRecord().getKey()); // Remove successor
        } else {
            BSTNode child;
            if (node.getLeftChild() != null) {
                child = node.getLeftChild(); // Use left child if it exists
            } else {
                child = node.getRightChild(); // Otherwise, use right child
            }

            if (node == root) {
                root = child; // Set new root
                child.setParent(null); // Child is root, so no parent
            } else {
                BSTNode par = node.getParent();
                if (par.getLeftChild() == node) {
                    par.setLeftChild(child); // Set parent's left child
                } else {
                    par.setRightChild(child); // Set parent's right child
                }
                child.setParent(par); // Set child's parent
            }
        }
    }

    /**
     * This function finds the successor of the node with the specified key in the subtree rooted at the given node.
     *
     * @param r the root of the subtree to search for the successor
     * @param k the key of the node whose successor is to be found
     * @return the successor node, or null if there is no successor
     */
    public BSTNode successor(BSTNode r, Key k) {
        BSTNode node = get(r, k);
        if (node == null) return null;

        if (node.getRightChild() != null) {
            return smallest(node.getRightChild()); // Find smallest in right subtree
        } else {
            BSTNode par = node.getParent();
            while (par != null && node == par.getRightChild()) {
                node = par; // Move up the tree to find successor
                par = par.getParent();
            }
            return par; // Return successor if found, otherwise null
        }
    }

    /**
     * This function finds the predecessor of the node with the specified key in the subtree rooted at the given node.
     *
     * @param r the root of the subtree to search for the predecessor
     * @param k the key of the node whose predecessor is to be found
     * @return the predecessor node, or null if there is no predecessor
     */
    public BSTNode predecessor(BSTNode r, Key k) {
        BSTNode node = get(r, k);
        if (node == null) return null;
    
        if (node.getLeftChild() != null) {
            return largest(node.getLeftChild()); // Find largest in left subtree
        }

        // Search in the ancestors if no left child
        BSTNode par = node.getParent();
        while (par != null && node == par.getLeftChild()) {
            node = par;
            par = par.getParent();
        }
        return par;
    }

    /**
     * This function finds the smallest node in the subtree rooted at the given node.
     *
     * @param r the root of the subtree to find the smallest node
     * @return the smallest node in the subtree, or null if the subtree is empty
     */
    public BSTNode smallest(BSTNode r) {
        if (r == null || r.getLeftChild() == null) {
            return r; // Return if node is smallest or subtree is empty
        }
        return smallest(r.getLeftChild()); // Recurse left to find smallest
    }

    /**
     * This function finds the largest node in the subtree rooted at the given node.
     *
     * @param r the root of the subtree to find the largest node
     * @return the largest node in the subtree, or null if the subtree is empty
     */
    public BSTNode largest(BSTNode r) {
        if (r == null || r.getRightChild() == null) {
            return r; // Return if node is largest or subtree is empty
        }
        return largest(r.getRightChild()); // Recurse right to find largest
    }
}
