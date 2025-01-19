import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Maze.java
 * Ali Ajwani
 * Nov 30, 2024
 * 
 * This class represents a maze, modeled as a graph, and provides methods to
 * read a maze from an input file, solve the maze, and retrieve the graph.
 */
public class Maze {

    /**
     * This field represents the graph structure of the maze.
     */
    private Graph graph;

    /**
     * This field represents the starting node in the maze.
     */
    private int firstNode;

    /**
     * This field represents the ending node in the maze.
     */
    private int lastNode;

    /**
     * This field represents the number of coins available to traverse the maze.
     */
    private int numCoins;

    /**
     * This field represents the path taken to solve the maze.
     */
    private List<GraphNode> path;

    /**
     * This function initializes a Maze object by reading the maze data from an
     * input file and building the graph structure.
     *
     * @param inputFile the name of the input file containing the maze data
     * @throws MazeException if an error occurs while reading the file or building
     *                       the graph
     */
    public Maze(String inputFile) throws MazeException {
        try {
			BufferedReader inputReader = new BufferedReader(new FileReader(inputFile));
			readAndProcess(inputReader);
		} catch (IOException | GraphException e) {
			throw new MazeException("Error reading the input file.");
		}
		
    }

    /**
     * This function retrieves the graph representing the maze.
     *
     * @return the graph of the maze
     * @throws MazeException if the graph is not defined
     */
    public Graph getGraph() throws MazeException {
        if (this.graph == null) {
            throw new MazeException("The graph is not defined.");
        }
        return this.graph;
    }

    /**
     * This function solves the maze and returns an iterator over the nodes in the
     * solution path.
     *
     * @return an iterator over the nodes in the solution path, or null if no
     *         solution exists
     */
    public Iterator<GraphNode> solve() {
        path = new ArrayList<>();
        try {
            // Perform a depth-first search starting from the first node
            Iterator<GraphNode> result = depthFirstSearch(numCoins, graph.getNode(firstNode));

            // Return the path iterator if a solution was found
            if (result != null) {
                return result;
            }
        } catch (GraphException e) {
            // Print an error message in case of an exception
            System.err.println("Error: " + e.getMessage());
        }
        return null; // Return null if no solution was found
    }

    /**
     * This function performs a Depth-First Search (DFS) to find a path from the
     * current node to the end node.
     *
     * @param coinsLeft the number of coins remaining to traverse the maze
     * @param currNode the current node in the maze
     * @return an iterator over the solution path if found, or null if no path
     *         exists
     * @throws GraphException if an error occurs during graph traversal
     */
    private Iterator<GraphNode> depthFirstSearch(int coinsLeft, GraphNode currNode) throws GraphException {
		// Add the current node to the path
		path.add(currNode);
	
		// Check if the current node is the end node
		if (currNode.getName() == lastNode) {
			return path.iterator();
		}
	
		// Mark the current node as visited
		currNode.mark(true);
	
		// Get all incident edges and iterate using a for-each style
		Iterator<GraphEdge> incidentEdges = graph.incidentEdges(currNode);
		if (incidentEdges != null) {
			for (; incidentEdges.hasNext(); ) {
				GraphEdge edge = incidentEdges.next();
	
				// Determine the neighbor node
				GraphNode neighborNode = (edge.secondEndpoint().equals(currNode)) ? edge.firstEndpoint() : edge.secondEndpoint();
	
				// Process unmarked neighbors
				if (!neighborNode.isMarked()) {
					int coinsRequired = edge.getType();
	
					// Check if there are enough coins to traverse the edge
					if (coinsLeft >= coinsRequired) {
						Iterator<GraphNode> result = depthFirstSearch(coinsLeft - coinsRequired, neighborNode);
	
						// If a valid path is found, return it
						if (result != null) {
							return result;
						}
					}
				}
			}
		}
	
		// unmark the node and remove it from the path
		currNode.mark(false);
		path.remove(path.size() - 1);
	
		return null; // No path found
	}
	

    /**
 	* This function reads and processes the maze parameters and structure from the
 	* input file in a single step, while also inserting edges based on the maze layout.
 	*
 	* @param inputReader the BufferedReader used to read the maze data
 	* @throws IOException if an I/O error occurs
 	* @throws GraphException if an error occurs while constructing the graph
 	*/
	private void readAndProcess(BufferedReader inputReader) throws IOException, GraphException {
        int S = Integer.parseInt(inputReader.readLine().trim()); // Reads and ignores maze size
		int Col = Integer.parseInt(inputReader.readLine().trim()); // Number of columns in the maze
        int Row = Integer.parseInt(inputReader.readLine().trim()); // Number of rows in the maze
        numCoins = Integer.parseInt(inputReader.readLine().trim()); // Number of coins available
		 
		// Initialize the graph with the appropriate size
        graph = new Graph(Col * Row);
        for (int i = 0; i < Row; i++) {
            String roomRow = inputReader.readLine().trim(); // Read the room row
            String corridorRow = (i < Row - 1) ? inputReader.readLine().trim() : null; // Read the corridor row if applicable

            for (int j = 0; j < Col; j++) {
                int node1 = i * Col + j; // Calculate the node index for the current room
                char room = roomRow.charAt(2 * j); // Identify the room type

                if (room == 's') {
                    firstNode = node1; // Set the start node
                } else if (room == 'x') {
                    lastNode = node1; // Set the end node
                }

                // Add edges for horizontal connections
                if (j < Col - 1) {
                    char horizontalLink = roomRow.charAt(2 * j + 1);
                    addEdge(node1, node1 + 1, horizontalLink); 
				}
                // Add edges for vertical connections
                if (corridorRow != null) {
                    char verticalLink = corridorRow.charAt(2 * j);
                    addEdge(node1, node1 + Col, verticalLink); 
                }           
			}
		}
	}

	/**
 	* This function adds an edge to the graph based on the connection type.
 	*
 	* @param node1 the first node
	* @param node2 the second node
	* @param linkType the type of connection between the nodes
	* @throws GraphException if an invalid link type is provided
 	*/
	private void addEdge(int node1, int node2, char linkType) throws GraphException {
	    // Determine the type of connection and insert the appropriate edge
		switch (linkType) {
	        // Corridor: Create an edge with a cost of 0
			case 'c':
	            graph.insertEdge(graph.getNode(node1), graph.getNode(node2), 0, "corridor");
            break;
	        // Wall: No edge is created, as walls block movement
			case 'w':
	            break;
	         // Check if the link type represents a locked door
				default:
	            if (Character.isDigit(linkType)) {
	                int coinsRequired = Character.getNumericValue(linkType);
	                graph.insertEdge(graph.getNode(node1), graph.getNode(node2), coinsRequired, "door");
	            } else {
	                throw new GraphException("Invalid link type: " + linkType);  // Error if invalid link type
	            }
	    }
	}
}
