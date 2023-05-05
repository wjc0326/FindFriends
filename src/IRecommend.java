import java.util.*;

public interface IRecommend {

    /**
     * Get the information of current user
     * @param database used to get info
     * @param username the name of this user
     */
    Map<String, String> getThisUserInfo(SocialDatabase database, String username);

    /**
     * Use database to get all users that currently are not this user's friends
     * @param database used to get info
     * @param username the name of this user
     */
    List<String> getNewFriends(SocialDatabase database, String username);

    /**
     * Get the information of new friends
     * @param database used to get info
     * @param username the name of this user
     */
    Map<String, Map<String, String>> getNewFriendsInfo(SocialDatabase database, String username);

    /**
     * Compute the hobby scores of new friends
     */
    Map<String, Double> hobbyScore();

    /**
     * Compute the work scores of new friends
     */
    Map<String, Double> workScore();

    /**
     * Compute the age scores of new friends
     */
    Map<String, Double> ageScore();

    /**
     * Compute the popularity scores of new friends
     * @param database used to get info
     */
    Map<String, Double> popularityScore(SocialDatabase database);

    /**
     * Compute the combined scores of new friends, given two standards
     * @param wordList the standards user choose, sort by importance
     */
    List<String> twoWordsScore(SocialDatabase database, List<String> wordList);

    /**
     * Compute the combined scores of new friends, given three standards
     * @param wordList the standards user choose, sort by importance
     */
    List<String> threeWordsScore(SocialDatabase database, List<String> wordList);

    /**
     * Compute the combined scores of new friends, given four standards
     * @param wordList the standards user choose, sort by importance
     */
    List<String> fourWordsScore(SocialDatabase database, List<String> wordList);

    /**
     * Get the list of new friends to recommend, sort by score
     * @param wordList the standards user choose, sort by importance
     */
    List<String> newFriendsSorted(SocialDatabase database, List<String> wordList);

}
