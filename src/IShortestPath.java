import java.util.List;

public interface IShortestPath {

    /**
     * Get the shortest path from current user to his future friend
     * @param username current user
     * @param friend you want to connect
     */
    List<Integer> getShortesetPath(SocialDatabase database, String username, String friend);

    List<Integer> getShortesetPathDFS(SocialDatabase database, String username, String friend);
}
