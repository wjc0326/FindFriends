public interface IGraph {
    /**
     * Initialize the graph
     */
    void init();


    /**
     * @return the number of vertices in current graph
     */
    int nodeNum();


    /**
     * @return the number of edges in current graph
     */
    int edgeNum();


    /**
     * Get the vertex information associate with index v
     *
     * @param v the index of the vertex
     * @return the information of vertex
     */

    Object getVertexInfo(int v);


    /**
     * Set the vertex information associate with index v
     *
     * @param v   the index of the vertex
     * @param info the information of the vertex
     */
    void setVertexInfo(int v, Object info);


    /**
     * Adds a new edge from vertex v to vertex u with weight w
     *
     * @param v   - the out vertex
     * @param u   - the in vertex
     * @param w - the weight of the edge
     */
    void addEdge(int v, int u, double w);


    /**
     * Get the weight of an edge
     *
     * @param v - the out vertex
     * @param u - the in vertex
     * @return the weight of the (v,u) edge
     */
    double getWeight(int v, int u);


    /**
     * Removes the edge from the graph
     *
     * @param v - the out vertex
     * @param u - the in vertex
     */
    void removeEdge(int v, int u);


    /**
     * Check whether there is an edge between two vertex
     *
     * @param v - the out vertex
     * @param u - the in vertex
     * @return true if the there is an edge between v and u, else return false
     */
    boolean hasEdge(int v, int u);


    /**
     * Get all the adjacent vertices of given vertex
     *
     * @param v - the given vertex
     * @return an array of adjacent vertices' index
     */
    int[] getAdj(int v);

}
