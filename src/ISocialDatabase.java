import java.util.Map;
import java.util.Set;

public interface ISocialDatabase {

    /**
     * get a userNode by username or user id
     * @param user given username or id
     * @return a userNode
     */
    UserNode getUser(Object user);

    /**
     * get a user information by username or user id
     * @param user given username or id
     * @return a map contains specific user's information
     */
    Map<String, String> getUserInfo(Object user);

    /**
     * get a user friends list by username or user id
     * @param user given username or id
     * @return a list contains specific user's friends information
     */
    Set<UserNode> getFriends(Object user);

    /**
     * @return the total number of user in this social database
     */
    int userNum();

    /**
     * @return the total number of friend pair in this social database
     */
    int connectionNum();

    /**
     * add a new user to this social database with given username and information
     * @param name given username
     * @param info user information
     * @return true if add successfully, false if suggest username already exist
     */
    boolean addUser(String name, String info);

    /**
     * add a friend pair to this social database
     * @param user1 the name or id of a user
     * @param user2 the name or id of another user
     */
    void addConnection(Object user1, Object user2);

    /**
     * @return a collection of all user's name and their associate id
     */
    Map<String, Integer> getAllUser();

    /**
     * print out a specific user's information
     * @param user given username or id
     */
    void printUserInfo(Object user);

    /**
     * use to write the database to a given file
     * @param filename given filename
     */
    void writeToFile(String filename);

    /**
     * @return the time used to create the database
     */
    long getTime();

}
