import java.util.*;

public class ShortestPath implements IShortestPath{
    /**
     * Get the shortest path from current user to his future friend
     *
     * @param username current user
     * @param friend   you want to connect
     */
    @Override
    public List<Integer> getShortesetPath(SocialDatabase database, String username, String friend) {
        int u = database.getUser(username).getID();
        int v = database.getUser(friend).getID();
        if (u == v) {
            return new ArrayList<>(Arrays.asList(u));
        }

        int numOfVertices = database.getAllUser().size();

        boolean[] visited = new boolean[numOfVertices + 1];
        int[] parent = new int[numOfVertices + 1];
        Arrays.fill(parent, -1);
        Queue<Integer> queue = new LinkedList<>();

        visited[u] = true;
        queue.add(u);

        while (!queue.isEmpty()) {
            int currentNode = queue.poll();

            for (UserNode nei : database.getFriends(currentNode)) {
                int neighbor = nei.getID();
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    parent[neighbor] = currentNode;
                    queue.add(neighbor);

                    if (neighbor == v) {
                        List<Integer> path = new ArrayList<>();
                        int node = v;
                        while (node != u) {
                            path.add(node);
                            node = parent[node];
                        }
                        path.add(u);
                        Collections.reverse(path);
                        return path;
                    }
                }
            }
        }
        return new ArrayList<>();
    }

    @Override
    public List<Integer> getShortesetPathDFS(SocialDatabase database, String username, String friend) {
        int numOfVertices = database.getAllUser().size();
        boolean[] visited = new boolean[numOfVertices + 1];
        List<Integer> currentPath = new ArrayList<>();
        List<Integer> shortestPath = new ArrayList<>();

        int u = database.getUser(username).getID();
        int v = database.getUser(friend).getID();

        dfsUtil(database, u, v, visited, currentPath, shortestPath);
        return shortestPath;
    }

    private void dfsUtil(SocialDatabase database, int currentNode, int targetNode, boolean[] visited, List<Integer> currentPath, List<Integer> shortestPath) {
        visited[currentNode] = true;
        currentPath.add(currentNode);

        if (currentNode == targetNode) {
            if (shortestPath.isEmpty() || currentPath.size() < shortestPath.size()) {
                shortestPath.clear();
                shortestPath.addAll(currentPath);
            }
        } else {
            for (UserNode nei : database.getFriends(currentNode)) {
                int neighbor = nei.getID();
                if (!visited[neighbor]) {
                    dfsUtil(database, neighbor, targetNode, visited, currentPath, shortestPath);
                }
            }
        }

        visited[currentNode] = false;
        currentPath.remove(currentPath.size() - 1);
    }
}
