import java.util.Set;

public interface IGraph {

    /**
     * @return the number of vertices in current graph
     */
    int nodeNum();


    /**
     * @return the number of edges in current graph
     */
    int edgeNum();


    /**
     * Adds a new Vertex
     *
     * @param v   - the given vertex
     */
    void addVertex(UserNode v);

    /**
     * Adds a new edge from vertex v to vertex u
     *
     * @param v   - the out vertex
     * @param u   - the in vertex
     */
    void addEdge(UserNode v, UserNode u);


    /**
     * Removes the edge from the graph
     *
     * @param v - the out vertex
     * @param u - the in vertex
     */
    void removeEdge(UserNode v, UserNode u);


    /**
     * Check whether there is an edge between two vertex
     *
     * @param v - the out vertex
     * @param u - the in vertex
     * @return true if the there is an edge between v and u, else return false
     */
    boolean hasEdge(UserNode v, UserNode u);


    /**
     * Get all the adjacent vertices of given vertex
     *
     * @param v - the given vertex
     * @return a set of adjacent vertices
     */
    Set<UserNode> getAdj(UserNode v);

    /**
     * Get all the vertices in graph
     *
     * @return a set of adjacent vertices
     */
    Set<UserNode> getVertices();

}
