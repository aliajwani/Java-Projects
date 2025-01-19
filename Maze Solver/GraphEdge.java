/**
 * GraphEdge.java
 * Ali Ajwani
 * 
 * This class represents an edge in a graph, connecting two nodes and
 * storing information about the edge type and label.
 */
public class GraphEdge {

    /**
     * The first endpoint of the edge.
     */
    private GraphNode first_end_point;

    /**
     * The second endpoint of the edge.
     */
    private GraphNode second_end_point;

    /**
     * The type of the edge.
     */
    private int type;

    /**
     * The label associated with the edge.
     */
    private String label;

    /**
     * Creates an edge with the specified endpoints, type, and label.
     *
     * @param u the first endpoint
     * @param v the second endpoint
     * @param type  the type of the edge
     * @param label the label of the edge
     */
    public GraphEdge(GraphNode u, GraphNode v, int type, String label) {
        this.first_end_point = u;
        this.second_end_point = v;
        this.type = type;
        this.label = label;
    }

    /**
     * Returns the first endpoint of the edge.
     *
     * @return the first endpoint
     */
    public GraphNode firstEndpoint() {
        return this.first_end_point;
    }

    /**
     * Returns the second endpoint of the edge.
     *
     * @return the second endpoint
     */
    public GraphNode secondEndpoint() {
        return this.second_end_point;
    }

    /**
     * Returns the type of the edge.
     *
     * @return the edge type
     */
    public int getType() {
        return this.type;
    }

    /**
     * Sets the type of the edge.
     *
     * @param newType the edge type
     */
    public void setType(int newType) {
        this.type = newType;
    }

    /**
     * Returns the label of the edge.
     *
     * @return the edge label
     */
    public String getLabel() {
        return this.label;
    }

    /**
     * Sets the label of the edge.
     *
     * @param label the edge label
     */
    public void setLabel(String label) {
        this.label = label;
    }
}
