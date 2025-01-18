import java.util.ArrayList;
import java.util.Iterator;

/**
 * Graph.java
 * Ali Ajwani
 * Nov 30, 2024
 * 
 * This class represents a graph structure with nodes and edges,
 * using an adjacency matrix for edge representation.
 */
public class Graph implements GraphADT {

    /**
     * This field represents the number of nodes in the graph.
     */
    private int edges;

    /**
     * This field represents the array of nodes in the graph.
     */
    private GraphNode[] nodes;

    /**
     * This field represents the adjacency matrix to store edges between nodes.
     */
    private GraphEdge[][] adjacent_edges;

    /**
     * This function initializes the graph with the specified number of nodes.
     *
     * @param n the number of nodes
     */
    public Graph(int n) {
        this.edges = n;
        this.nodes = new GraphNode[n];
        this.adjacent_edges = new GraphEdge[n][n];

        // Initialize the graph by creating nodes
        int i = 0;
        while (i < n) {
            nodes[i] = new GraphNode(i); // Assign each node a unique name
            i++;
        }
    }

    /**
     * This function inserts an edge between two nodes in the graph.
     *
     * @param nodeu the first node
     * @param nodev the second node
     * @param edgeType  the type of the edge
     * @param label the label of the edge
     * @throws GraphException if the nodes are invalid or the edge already exists
     */
    @Override
    public void insertEdge(GraphNode nodeu, GraphNode nodev, int edgeType, String label) throws GraphException {
        // Ensure both nodes exist within the graph
        if (nodeu.getName() < 0 || nodeu.getName() >= edges || nodev.getName() < 0 || nodev.getName() >= edges) {
            throw new GraphException("The node does not exist.");
        }

        // Check if an edge already exists between the two nodes
        if (adjacent_edges[nodeu.getName()][nodev.getName()] != null) {
            throw new GraphException("The edge already exists.");
        }

        // Add the edge to the adjacency matrix
        adjacent_edges[nodeu.getName()][nodev.getName()] = new GraphEdge(nodeu, nodev, edgeType, label);
        adjacent_edges[nodev.getName()][nodeu.getName()] = new GraphEdge(nodeu, nodev, edgeType, label);
    }

    /**
     * This function retrieves a node by its identifier.
     *
     * @param name the identifier of the node
     * @return the corresponding node
     * @throws GraphException if the node does not exist
     */
    @Override
    public GraphNode getNode(int name) throws GraphException {
        // Ensure the node exists within the graph bounds
        if (nodes[name] == null || name < 0 || name >= edges) {
            throw new GraphException("The node does not exist.");
        }
        return nodes[name]; // Return the requested node
    }

    /**
     * This function retrieves all edges incident to a given node.
     *
     * @param u the node for which to retrieve incident edges
     * @return an iterator over the incident edges, or null if no edges exist
     * @throws GraphException if the node does not exist
     */
    @Override
    public Iterator<GraphEdge> incidentEdges(GraphNode u) throws GraphException {
        // Check if the node exists
        if (u.getName() >= edges || u.getName() < 0) {
            throw new GraphException("The node is not in the graph.");
        }

        ArrayList<GraphEdge> listOfEdges = new ArrayList<>();
        for (int i = 0; i < edges; i++) {
            GraphEdge edge = adjacent_edges[u.getName()][i]; // Get the edge from adjacency matrix
            if (edge != null) {
                listOfEdges.add(edge); // Add to the list if it exists
            }
        }

        // Return null if no edges exist, otherwise return the iterator
        return listOfEdges.isEmpty() ? null : listOfEdges.iterator();
    }

    /**
     * This function retrieves the edge between two nodes.
     *
     * @param u the first node
     * @param v the second node
     * @return the edge between the nodes
     * @throws GraphException if no edge exists or if the nodes are invalid
     */
    @Override
    public GraphEdge getEdge(GraphNode u, GraphNode v) throws GraphException {
        // Ensure both nodes are valid
        if (u.getName() < 0 || u.getName() >= edges || v.getName() < 0 || v.getName() >= edges) {
            throw new GraphException("The node does not exist.");
        }

        GraphEdge edge = adjacent_edges[u.getName()][v.getName()]; // Get the edge from adjacency matrix
        if (edge == null) {
            throw new GraphException("There is no shared edge between these nodes.");
        }
        return edge; // Return the found edge
    }

    /**
     * This function checks if two nodes are adjacent.
     *
     * @param u the first node
     * @param v the second node
     * @return true if the nodes share an edge, false otherwise
     * @throws GraphException if the nodes are invalid
     */
    @Override
    public boolean areAdjacent(GraphNode u, GraphNode v) throws GraphException {
        // Validate the nodes
        if (u.getName() < 0 || u.getName() >= edges || v.getName() < 0 || v.getName() >= edges) {
            throw new GraphException("There is a node that does not exist");
        }

        // Check if an edge exists between the two nodes
        return adjacent_edges[u.getName()][v.getName()] != null;
    }
}
