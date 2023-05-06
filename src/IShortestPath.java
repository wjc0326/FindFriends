import java.util.List;
import java.util.Map;

public interface IShortestPath {

    /**
     * Get the shortest path from current user to his future friend
     * @param sername current user
     * @param friend you want to connect
     */
    List<Integer> getShortesetPath(SocialDatabase database, String sername, String friend);

    List<Integer> getShortesetPathDFS(SocialDatabase database, String username, String friend);
}
