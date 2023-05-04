import org.junit.Test;

import static org.junit.Assert.*;

public class UserNodeTest {


    @Test
    public void getName() {
        UserNode user = new UserNode("Tancy", 1);
        assertEquals("Tancy", user.getName());
    }

    @Test
    public void getID() {
        UserNode user = new UserNode("Tancy", 1);
        assertEquals(1, user.getID());
    }

    @Test
    public void setName() {
        UserNode user = new UserNode("Tancy", 1);
        user.setName("Tianyun");
        assertEquals("Tianyun", user.getName());
    }

    @Test
    public void setID() {
        UserNode user = new UserNode("Tancy", 1);
        user.setID(2);
        assertEquals(2, user.getID());
    }

    @Test
    public void testEquals() {
        UserNode user1 = new UserNode("Tancy", 1);
        UserNode user2 = new UserNode("Tancy", 1);
        assertEquals(user1, user2);

        user1 = new UserNode("Tancy", 1);
        user2 = new UserNode("Tancy", 0);
        assertEquals(user1, user2);

        user1 = new UserNode("Tancy", 1);
        user2 = new UserNode(null, 1);
        assertEquals(user1, user2);

        user1 = new UserNode("Tancy", 1);
        user2 = new UserNode(null, 0);
        assertNotSame(user1, user2);

        user1 = new UserNode("Tancy", 1);
        user2 = new UserNode("Tancy", 2);
        assertNotSame(user1, user2);

        user1 = new UserNode("Tancy", 1);
        user2 = new UserNode("Tianyun", 1);
        assertNotSame(user1, user2);

        user1 = new UserNode("Tancy", 1);
        user2 = new UserNode("Tianyun", 2);
        assertNotSame(user1, user2);
    }

    @Test
    public void testHashCode() {
        // use userID as hash code
        UserNode user = new UserNode("Tancy", 1);
        assertEquals(1, user.hashCode());

        // use username as hash code
//        UserNode user = new UserNode("Tancy", 1);
//        assertEquals(80574391, user.hashCode());

        // use first four digit of username as hash code
//        UserNode user = new UserNode("Tancy", 1);
//        assertEquals(2599170, user.hashCode());

        // use first four digit of username and user id as hash code
//        UserNode user = new UserNode("Tancy", 1);
//        assertEquals(2599171, user.hashCode());

    }

    @Test
    public void printUser() {
        UserNode user = new UserNode("Tancy", 1);
        user.printUser();
    }
}