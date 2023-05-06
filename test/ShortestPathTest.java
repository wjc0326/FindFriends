import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ShortestPathTest {

    @Test
    public void testGetShortesetPath() {
        SocialDatabase database =
                new SocialDatabase("./test/RecommendTestFile/recommendDataSet.txt", 0);
        ShortestPath sp = new ShortestPath();
        List<List<String>> expectedPath = new ArrayList<>();
        expectedPath.add(Arrays.asList("A", "B", "C", "I"));
        expectedPath.add(Arrays.asList("A"));
        expectedPath.add(Arrays.asList("A", "B"));
        expectedPath.add(Arrays.asList("G", "D", "A", "E"));

        List<List<String>> pathParameters = new ArrayList<>();
        pathParameters.add(Arrays.asList("A", "I"));
        pathParameters.add(Arrays.asList("A", "A"));
        pathParameters.add(Arrays.asList("A", "B"));
        pathParameters.add(Arrays.asList("G", "E"));

        for (int i = 0; i < 4; i++) {
            String name = pathParameters.get(i).get(0);
            String friend = pathParameters.get(i).get(1);
            List<Integer> path = sp.getShortesetPath(database, name, friend);
            List<String> myFriends = new ArrayList<>();
            for (int f : path) {
                myFriends.add(database.getUser(f).getName());
            }
            assertEquals(expectedPath.get(i), myFriends);
        }
    }

    @Test
    public void testGetShortesetPathDFS() {
        SocialDatabase database =
                new SocialDatabase("./test/RecommendTestFile/recommendDataSet.txt", 0);
        ShortestPath sp = new ShortestPath();
        List<List<String>> expectedPath = new ArrayList<>();
        expectedPath.add(Arrays.asList("A", "B", "C", "I"));
        expectedPath.add(Arrays.asList("A"));
        expectedPath.add(Arrays.asList("A", "B"));
        expectedPath.add(Arrays.asList("G", "D", "A", "E"));

        List<List<String>> pathParameters = new ArrayList<>();
        pathParameters.add(Arrays.asList("A", "I"));
        pathParameters.add(Arrays.asList("A", "A"));
        pathParameters.add(Arrays.asList("A", "B"));
        pathParameters.add(Arrays.asList("G", "E"));

        for (int i = 0; i < 4; i++) {
            String name = pathParameters.get(i).get(0);
            String friend = pathParameters.get(i).get(1);
            List<Integer> path = sp.getShortesetPathDFS(database, name, friend);
            List<String> myFriends = new ArrayList<>();
            for (int f : path) {
                myFriends.add(database.getUser(f).getName());
            }
            assertEquals(expectedPath.get(i), myFriends);
        }
    }
    @Test
    public void testRunTime() {
        SocialDatabase database =
                new SocialDatabase("./test/RecommendTestFile/recommendDataSet.txt", 0);
        ShortestPath sp = new ShortestPath();

        long startTime = System.nanoTime();
        List<Integer> path = sp.getShortesetPath(database, "A", "I");
        long endTime = System.nanoTime();

        long elapsedTime = endTime - startTime;
        System.out
                .println("BFS Execution Time: " + elapsedTime + " nanoseconds");

        long startTimeDFS = System.nanoTime();
        List<Integer> pathDFS = sp.getShortesetPathDFS(database, "A", "I");
        long endTimeDFS = System.nanoTime();

        long elapsedTimeDFS = endTimeDFS - startTimeDFS;
        System.out
                .println("DFS Execution Time: " + elapsedTimeDFS + " nanoseconds");
    }
}