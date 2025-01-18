/**
 * BSTNode.java
 * Ali Ajwani
 * 251374819
 * 
 * This class represents a node in a binary search tree with each node containing
 * a record and reference to its left child, right child, and parent.
 */

public class BSTNode {

    private Record item;
    private BSTNode left, right, parent;

    /**
     * This function initializes a new BSTNode with the specified record.
     *
     * @param item the record associated with this node
     */
    public BSTNode(Record item) {
        this.item = item;
    }

    /**
     * This function returns the record associated with this node.
     *
     * @return the record of this node
     */
    public Record getRecord() {
        return item;
    }

    /**
     * This function sets the record associated with this node.
     *
     * @param d the record to set
     */
    public void setRecord(Record d) {
        this.item = d;
    }

    /**
     * This function returns the left child of this node.
     *
     * @return the left child node
     */
    public BSTNode getLeftChild() {
        return left;
    }

    /**
     * This function sets the left child of this node.
     *
     * @param u the node to set as the left child
     */
    public void setLeftChild(BSTNode u) {
        this.left = u;
    }

    /**
     * This function returns the right child of this node.
     *
     * @return the right child node
     */
    public BSTNode getRightChild() {
        return right;
    }

    /**
     * This function sets the right child of this node.
     *
     * @param u the node to set as the right child
     */
    public void setRightChild(BSTNode u) {
        this.right = u;
    }

    /**
     * This function returns the parent of this node.
     *
     * @return the parent node
     */
    public BSTNode getParent() {
        return parent;
    }

    /**
     * This function sets the parent of this node.
     *
     * @param u the node to set as the parent
     */
    public void setParent(BSTNode u) {
        this.parent = u;
    }

    /**
     * This function checks if the node is a leaf.
     *
     * @return true if the node is a leaf
     * @return false otherwise
     */
    public boolean isLeaf() {
        return left == null && right == null;
    }
}