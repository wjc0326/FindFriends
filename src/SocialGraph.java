import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SocialGraph implements IGraph {

    private Map<UserNode, Set<UserNode>> connectionList = new HashMap<>();
    private Set<UserNode> vertices = new HashSet<>();
    private int      numConnection = 0;  // number of the undirected edge in graph
    private int      numVertex = 0;  // number of the vertex in graph

    @Override
    public int nodeNum() {
        return numVertex;
    }

    @Override
    public int edgeNum() {
        return numConnection;
    }

    @Override
    public void addVertex(UserNode v) {
        if (!vertices.contains(v)) {
            // if the vertices is not in the graph yet
            vertices.add(v);  // add the vertex in graph
            Set<UserNode> empty = new HashSet<>();
            connectionList.put(v, empty);  // add an empty adjacency list for it
            numVertex ++;  // update vertices number
        }
    }

    @Override
    public void addEdge(UserNode v, UserNode u) {
        // make sure both vertex is in map
        addVertex(v);
        addVertex(u);

        if (!connectionList.get(v).contains(u)) {
            // if the target edge do not exist
            // add an edge to both vertex
            connectionList.get(v).add(u);
            connectionList.get(u).add(v);
            numConnection ++;  // update connection number
        }
    }

    @Override
    public void removeEdge(UserNode v, UserNode u) {
        if (vertices.contains(v) && vertices.contains(u)) {
            // both vertices exist
            if (connectionList.get(v).contains(u)) {
                // if edge exist
                // remove edge to both vertex
                connectionList.get(v).remove(u);
                connectionList.get(u).remove(v);
                numConnection --;  // update connection number
            }
        }

    }

    @Override
    public boolean hasEdge(UserNode v, UserNode u) {
        if (vertices.contains(v) && vertices.contains(u)) {
            // both vertices exist
            return connectionList.get(v).contains(u);
        }
        return false;
    }

    @Override
    public Set<UserNode> getAdj(UserNode v) {
        if (vertices.contains(v)) {
            // both vertex exist
            return connectionList.get(v);
        }
        return null;
    }

    @Override
    public Set<UserNode> getVertices() {
        return vertices;
    }


}
