import org.junit.Test;

import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

public class SocialDatabaseTest {

    @Test
    public void getUser() {
        SocialDatabase database =
                new SocialDatabase("./test/DatabaseTestFile/simpleTestDataFile.txt", 0);
        // get by name
        assertEquals("A", database.getUser("A").getName());
        assertEquals(1, database.getUser("A").getID());
        // get by ID
        assertEquals("A", database.getUser(1).getName());
        assertEquals(1, database.getUser(1).getID());
        // get not exist user
        assertNull(database.getUser("nobody"));
        // get by invalid input
        assertNull(database.getUser(1.0));
        assertNull(database.getUser(null));
    }

    @Test
    public void getUserInfo() {
        SocialDatabase database =
                new SocialDatabase("./test/DatabaseTestFile/simpleTestDataFile.txt", 0);
        // get exist user
        Map<String, String> result = database.getUserInfo("A");
        assertEquals(4, result.size());
        assertEquals("student", result.get("work"));
        assertEquals("crocheting", result.get("hobby"));
        assertEquals("B, C, D", result.get("friend"));

        // get non exist user
        result = database.getUserInfo("None");
        assertEquals(0, result.size());
    }

    @Test
    public void getFriends() {
        SocialDatabase database =
                new SocialDatabase("./test/DatabaseTestFile/simpleTestDataFile.txt", 0);
        // get exist user by name
        Set<UserNode> result = database.getFriends("A");
        assertEquals(3, result.size());
        assertTrue(result.contains(database.getUser("B")));
        assertTrue(result.contains(database.getUser("C")));
        assertTrue(result.contains(database.getUser("D")));
        // get exist user by id
        result = database.getFriends(1);
        assertEquals(3, result.size());
        assertTrue(result.contains(database.getUser("B")));
        assertTrue(result.contains(database.getUser("C")));
        assertTrue(result.contains(database.getUser("D")));
        // get non exist user
        result = database.getFriends("None");
        assertEquals(0, result.size());
    }

    @Test
    public void userNum() {
        SocialDatabase database =
                new SocialDatabase("./test/DatabaseTestFile/simpleTestDataFile.txt", 0);
        assertEquals(4, database.userNum());
    }

    @Test
    public void connectionNum() {
        SocialDatabase database =
                new SocialDatabase("./test/DatabaseTestFile/simpleTestDataFile.txt", 0);
        assertEquals(4, database.connectionNum());
    }

    @Test
    public void addUser() {
        SocialDatabase database =
                new SocialDatabase("./test/DatabaseTestFile/simpleTestDataFile.txt", 0);
        database.addUser("E", "Age:43; work: IT; Hobby: Tango; Friend: A");
        assertEquals(5, database.userNum());
        assertEquals(5, database.connectionNum());
        Set<UserNode> result = database.getFriends("A");
        assertEquals(4, result.size());
        assertTrue(result.contains(database.getUser("E")));
        Map<String, String> result2 = database.getUserInfo("E");
        assertEquals(4, result2.size());
        assertEquals("it", result2.get("work"));
        assertEquals("tango", result2.get("hobby"));
        assertEquals("A", result2.get("friend"));

        // add exist username
        assertFalse(database.addUser("E", " "));
    }

    @Test
    public void addConnection() {
        SocialDatabase database =
                new SocialDatabase("./test/DatabaseTestFile/simpleTestDataFile.txt", 0);
        database.addUser("E", "Age:43; work: IT; Hobby: Tango; Friend: A");
        database.addConnection("D","E");
        assertEquals(6, database.connectionNum());
        Set<UserNode> result = database.getFriends("E");
        assertEquals(2, result.size());
        assertTrue(result.contains(database.getUser("D")));

    }

    @Test
    public void getAllUser() {
        SocialDatabase database =
                new SocialDatabase("./test/DatabaseTestFile/simpleTestDataFile.txt", 0);
        Map<String, Integer> result = database.getAllUser();
        assertEquals(4, result.size());
        assertEquals(1, (int)result.get("A"));
        assertEquals(2, (int)result.get("B"));
        assertEquals(3, (int)result.get("C"));
        assertEquals(4, (int)result.get("D"));

    }

    @Test
    public void printUserInfo() {
        SocialDatabase database =
                new SocialDatabase("./test/DatabaseTestFile/simpleTestDataFile.txt", 0);
        database.printUserInfo("A");
        database.addUser("E", "Age:43; work: IT; Hobby: Tango; Friend: A");
        database.addConnection("D","E");
        database.printUserInfo("E");
    }

    @Test
    public void writeToFile() {
        SocialDatabase database =
                new SocialDatabase("./test/DatabaseTestFile/simpleTestDataFile.txt", 0);
        database.addUser("E", "Age:43; work: IT; Hobby: Tango; Friend: A");
        database.addConnection("D","E");
        database.writeToFile("./test/DatabaseTestFile/outDatabase.txt");
    }

    @Test
    public void getTime() {
        long[] time = {0, 0, 0, 0, 0};

        for (int i = 0; i < 10; i++) {
            SocialDatabase database0 =
                    new SocialDatabase("./test/DatabaseTestFile/createdDataSet.txt", 0);
            SocialDatabase database1 =
                    new SocialDatabase("./test/DatabaseTestFile/createdDataSet.txt", 1);
            SocialDatabase database2 =
                    new SocialDatabase("./test/DatabaseTestFile/createdDataSet.txt", 2);
            SocialDatabase database3 =
                    new SocialDatabase("./test/DatabaseTestFile/createdDataSet.txt", 3);
            SocialDatabase database4 =
                    new SocialDatabase("./test/DatabaseTestFile/createdDataSet.txt", 4);

            time[0] += database0.getTime();
            time[1] += database1.getTime();
            time[2] += database2.getTime();
            time[3] += database3.getTime();
            time[4] += database4.getTime();
        }

        System.out
                .println("HashCode Type: use user id as hash code \n" +
                "Average time used to create the database: " + time[0] / 10 + " millisecond");
        System.out
                .println("HashCode Type: use username as hash code \n" +
                "Average time used to create the database: " + time[1] / 10 + " millisecond");
        System.out
                .println("HashCode Type: use first four digit of username as hash code \n" +
                "Average time used to create the database: " + time[2] / 10 + " millisecond");
        System.out
                .println("HashCode Type: " +
                        "use first four digit of username and user id as hash code \n" +
                "Average time used to create the database: " + time[3] / 10 + " millisecond");
        System.out
                .println("HashCode Type: system default hash code \n" +
                "Average time used to create the database: " + time[4] / 10 + " millisecond");

    }
}