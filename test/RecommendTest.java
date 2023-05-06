import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class RecommendTest {

    @Test
    public void getThisUserInfo() {
        SocialDatabase database =
                new SocialDatabase("./test/RecommendTestFile/recommendDataSet.txt", 0);
        Recommend rec = new Recommend();
        Map<String, String> info = rec.getThisUserInfo(database, "A");

        assertEquals("reading, traveling, dancing, drawing", info.get("hobby"));
        assertEquals("25", info.get("age"));
        assertEquals("student", info.get("work"));
        assertEquals("B, D, E,", info.get("friend"));
    }

    @Test
    public void getNewFriends() {
        SocialDatabase database =
                new SocialDatabase("./test/RecommendTestFile/recommendDataSet.txt", 0);
        Recommend rec = new Recommend();
        List<String> recList = rec.getNewFriends(database, "A");

        assertEquals(5, recList.size());
        assertEquals("C", recList.get(0));
        assertEquals("F", recList.get(1));
        assertEquals("G", recList.get(2));
        assertEquals("H", recList.get(3));
        assertEquals("I", recList.get(4));
    }

    @Test
    public void getNewFriendsInfo() {
        SocialDatabase database =
                new SocialDatabase("./test/RecommendTestFile/recommendDataSet.txt", 0);
        Recommend rec = new Recommend();
        Map<String, Map<String, String>> friendsInfo = rec.getNewFriendsInfo(database, "A");

        assertEquals(5, friendsInfo.size());
        assertEquals("24", friendsInfo.get("C").get("age"));
    }

    @Test
    public void hobbyScore() {
        SocialDatabase database =
                new SocialDatabase("./test/RecommendTestFile/recommendDataSet.txt", 0);
        Recommend rec = new Recommend();
        Map<String, Map<String, String>> friendsInfo = rec.getNewFriendsInfo(database, "A");

        Map<String, Double> hobby = rec.hobbyScore();
        assertEquals(5, hobby.size());
        assertEquals(0.75, hobby.get("C"), 0.01);
        assertEquals(1, hobby.get("F"), 0.01);
        assertEquals(0.5, hobby.get("G"), 0.01);
        assertEquals(0.5, hobby.get("H"), 0.01);
        assertEquals(0.25, hobby.get("I"), 0.01);
    }

    @Test
    public void workScore() {
        SocialDatabase database =
                new SocialDatabase("./test/RecommendTestFile/recommendDataSet.txt", 0);
        Recommend rec = new Recommend();
        Map<String, Map<String, String>> friendsInfo = rec.getNewFriendsInfo(database, "A");

        Map<String, Double> work = rec.workScore();
        assertEquals(5, work.size());
        assertEquals(1, work.get("C"), 0.1);
        assertEquals(1, work.get("F"), 0.1);
        assertEquals(0, work.get("G"), 0.1);
        assertEquals(0, work.get("H"), 0.1);
        assertEquals(0, work.get("I"), 0.1);
    }

    @Test
    public void ageScore() {
        SocialDatabase database =
                new SocialDatabase("./test/RecommendTestFile/recommendDataSet.txt", 0);
        Recommend rec = new Recommend();
        Map<String, Map<String, String>> friendsInfo = rec.getNewFriendsInfo(database, "A");

        Map<String, Double> age = rec.ageScore();
        assertEquals(5, age.size());
        assertEquals(0.96, age.get("C"), 0.01);
        assertEquals(0.96, age.get("F"), 0.01);
        assertEquals(0.92, age.get("G"), 0.01);
        assertEquals(1, age.get("H"), 0.01);
        assertEquals(0.88, age.get("I"), 0.01);
    }

    @Test
    public void popularityScore() {
        SocialDatabase database =
                new SocialDatabase("./test/RecommendTestFile/recommendDataSet.txt", 0);
        Recommend rec = new Recommend();
        Map<String, Map<String, String>> friendsInfo = rec.getNewFriendsInfo(database, "A");

        Map<String, Double> popularity = rec.popularityScore(database);
        assertEquals(5, popularity.size());
        assertEquals(0.18, popularity.get("C"), 0.01);
        assertEquals(0.18, popularity.get("F"), 0.01);
        assertEquals(0.18, popularity.get("G"), 0.01);
        assertEquals(0.18, popularity.get("H"), 0.01);
        assertEquals(0.36, popularity.get("I"), 0.01);
    }

    @Test
    public void newFriendsSorted() {
        SocialDatabase database =
                new SocialDatabase("./test/RecommendTestFile/recommendDataSet.txt", 0);
        Recommend rec = new Recommend();
        Map<String, Map<String, String>> friendsInfo = rec.getNewFriendsInfo(database, "A");

        // TEST FOR ONE WORD
        List<String> wordList = Arrays.asList("hobby");
        List<String> result = rec.newFriendsSorted(database, wordList);
        assertEquals(5, result.size());
        assertEquals("F", result.get(0));
        assertEquals("C", result.get(1));
        assertEquals("G", result.get(2));
        assertEquals("H", result.get(3));
        assertEquals("I", result.get(4));

        wordList = Arrays.asList("age");
        result = rec.newFriendsSorted(database, wordList);
        assertEquals(5, result.size());
        assertEquals("H", result.get(0));
        assertEquals("C", result.get(1));
        assertEquals("F", result.get(2));
        assertEquals("G", result.get(3));
        assertEquals("I", result.get(4));

        wordList = Arrays.asList("work");
        result = rec.newFriendsSorted(database, wordList);
        assertEquals(5, result.size());
        assertEquals("C", result.get(0));
        assertEquals("F", result.get(1));
        assertEquals("G", result.get(2));
        assertEquals("H", result.get(3));
        assertEquals("I", result.get(4));

        wordList = Arrays.asList("popular");
        result = rec.newFriendsSorted(database, wordList);
        assertEquals(5, result.size());
        assertEquals("I", result.get(0));
        assertEquals("C", result.get(1));
        assertEquals("F", result.get(2));
        assertEquals("G", result.get(3));
        assertEquals("H", result.get(4));

        // TEST FOR DEFAULT (NO WORDS)
        wordList = new ArrayList<>();
        result = rec.newFriendsSorted(database, wordList);
        assertEquals(5, result.size());
        assertEquals("F", result.get(0));
        assertEquals("C", result.get(1));
        assertEquals("H", result.get(2));
        assertEquals("G", result.get(3));
        assertEquals("I", result.get(4));

        // TEST FOR TWO WORDS
        wordList = Arrays.asList("hobby", "age");
        result = rec.newFriendsSorted(database, wordList);
        assertEquals(5, result.size());
        assertEquals("F", result.get(0));
        assertEquals("C", result.get(1));
        assertEquals("H", result.get(2));
        assertEquals("G", result.get(3));
        assertEquals("I", result.get(4));

        // TEST FOR THREE WORDS
        wordList = Arrays.asList("age", "hobby", "work");
        result = rec.newFriendsSorted(database, wordList);
        assertEquals(5, result.size());
        assertEquals("F", result.get(0));
        assertEquals("C", result.get(1));
        assertEquals("H", result.get(2));
        assertEquals("G", result.get(3));
        assertEquals("I", result.get(4));

        // TEST FOR FOUR WORDS
        wordList = Arrays.asList("popular", "age", "hobby", "work");
        result = rec.newFriendsSorted(database, wordList);
        assertEquals(5, result.size());
        assertEquals("F", result.get(0));
        assertEquals("C", result.get(1));
        assertEquals("I", result.get(2));
        assertEquals("H", result.get(3));
        assertEquals("G", result.get(4));
    }
}