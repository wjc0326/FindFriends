import org.junit.Test;

import static org.junit.Assert.*;

public class SocialGraphTest {

    @Test
    public void nodeNum() {
        SocialGraph graph = new SocialGraph();
        graph.addVertex(new UserNode("Tancy", 1));
        assertEquals(1, graph.nodeNum());
        graph.addVertex(new UserNode("Tianyun", 2));
        assertEquals(2, graph.nodeNum());
    }

    @Test
    public void edgeNum() {
        SocialGraph graph = new SocialGraph();
        UserNode user1 = new UserNode("Tancy", 1);
        UserNode user2 = new UserNode("Tianyun", 2);
        UserNode user3 = new UserNode("Tansy", 3);
        graph.addVertex(user1);
        graph.addEdge(user1, user2);
        assertEquals(2, graph.nodeNum());
        assertEquals(1, graph.edgeNum());
        graph.addVertex(user3);
        graph.addEdge(user1, user3);
        assertEquals(3, graph.nodeNum());
        assertEquals(2, graph.edgeNum());
    }

    @Test
    public void addVertex() {
        SocialGraph graph = new SocialGraph();
        UserNode user1 = new UserNode("Tancy", 1);
        UserNode user2 = new UserNode("Tianyun", 2);
        // add same vertex twice
        graph.addVertex(user1);
        graph.addVertex(user1);
        assertEquals(1, graph.nodeNum());
        graph.addVertex(user2);
        assertEquals(2, graph.nodeNum());
        assertTrue(graph.getVertices().contains(user1));
        assertTrue(graph.getVertices().contains(user2));
    }

    @Test
    public void addEdge() {
        SocialGraph graph = new SocialGraph();
        UserNode user1 = new UserNode("Tancy", 1);
        UserNode user2 = new UserNode("Tianyun", 2);
        UserNode user3 = new UserNode("Tansy", 3);
        // add same edge twice
        graph.addVertex(user1);
        graph.addEdge(user1, user2);
        graph.addEdge(user1, user2);
        assertEquals(2, graph.nodeNum());
        assertEquals(1, graph.edgeNum());
        assertTrue(graph.hasEdge(user1, user2));
        graph.addVertex(user3);
        graph.addEdge(user1, user3);
        assertEquals(3, graph.nodeNum());
        assertEquals(2, graph.edgeNum());
        assertTrue(graph.hasEdge(user1, user3));
        assertFalse(graph.hasEdge(user2, user3));
    }

    @Test
    public void removeEdge() {
        SocialGraph graph = new SocialGraph();
        UserNode user1 = new UserNode("Tancy", 1);
        UserNode user2 = new UserNode("Tianyun", 2);
        UserNode user3 = new UserNode("Tansy", 3);
        graph.addVertex(user1);
        graph.addEdge(user1, user2);
        assertEquals(2, graph.nodeNum());
        assertEquals(1, graph.edgeNum());
        assertTrue(graph.hasEdge(user1, user2));
        graph.removeEdge(user1, user2);
        assertEquals(2, graph.nodeNum());
        assertEquals(0, graph.edgeNum());
        assertFalse(graph.hasEdge(user1, user2));
        graph.removeEdge(user1, user2);
        assertEquals(2, graph.nodeNum());
        assertEquals(0, graph.edgeNum());
        assertFalse(graph.hasEdge(user1, user2));
        graph.removeEdge(user1, user3);
        assertEquals(2, graph.nodeNum());
        assertEquals(0, graph.edgeNum());
        assertFalse(graph.hasEdge(user1, user2));
    }

    @Test
    public void hasEdge() {
        SocialGraph graph = new SocialGraph();
        UserNode user1 = new UserNode("Tancy", 1);
        UserNode user2 = new UserNode("Tianyun", 2);
        UserNode user3 = new UserNode("Tansy", 3);
        graph.addEdge(user1, user2);
        assertFalse(graph.hasEdge(user2, user3));
        graph.addEdge(user1, user3);
        assertEquals(3, graph.nodeNum());
        assertEquals(2, graph.edgeNum());
        assertTrue(graph.hasEdge(user1, user3));
        assertFalse(graph.hasEdge(user2, user3));
    }

    @Test
    public void getAdj() {
        SocialGraph graph = new SocialGraph();
        UserNode user1 = new UserNode("Tancy", 1);
        UserNode user2 = new UserNode("Tianyun", 2);
        UserNode user3 = new UserNode("Tansy", 3);
        graph.addEdge(user1, user2);
        graph.addEdge(user1, user3);

        assertTrue(graph.getAdj(user1).contains(user2));
        assertTrue(graph.getAdj(user1).contains(user3));
    }

    @Test
    public void getVertices() {
        SocialGraph graph = new SocialGraph();
        UserNode user1 = new UserNode("Tancy", 1);
        UserNode user2 = new UserNode("Tianyun", 2);
        UserNode user3 = new UserNode("Tansy", 3);
        graph.addVertex(user1);
        graph.addVertex(user2);
        assertTrue(graph.getVertices().contains(user1));
        assertTrue(graph.getVertices().contains(user2));
        assertFalse(graph.getVertices().contains(user3));
    }
}