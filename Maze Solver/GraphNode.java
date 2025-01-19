/**
 * GraphNode.java
 * Ali Ajwani
 * 
 * This class represents a node in a graph, identified by a unique name
 * and a marked state for traversal purposes.
 */
public class GraphNode {

    /**
     * This field represents the unique identifier for the node.
     */
    private int name;

    /**
     * This field represents the marked state of the node, used for graph traversal.
     */
    private boolean marked;

    /**
     * This function creates a graph node with the specified name and initializes it as unmarked.
     *
     * @param name the unique identifier for the node
     */
    public GraphNode(int name) {
        this.name = name;
        this.marked = false;
    }

    /**
     * This function sets the marked state of the node.
     *
     * @param mark true to mark the node, false to unmark it
     */
    public void mark(boolean mark) {
        this.marked = mark;
    }

    /**
     * This function checks whether the node is marked.
     *
     * @return true if the node is marked, false otherwise
     */
    public boolean isMarked() {
        return this.marked;
    }

    /**
     * This function retrieves the unique identifier of the node.
     *
     * @return the node's unique identifier
     */
    public int getName() {
        return this.name;
    }
}
