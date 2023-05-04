import java.util.List;
import java.util.Map;

public interface ISocialDatabase {

    /**
     * Initialize the database with input file
     * @param filename the name of a file contains the user data infomation
     */
    void init(String filename);

    /**
     * use to create the HashMap store the user info with input file
     * @param filename the name of a file contains the user data information
     */
    void createDataBase(String filename);

    /**
     * use the stored user infomation create a social network graph
     */
    void createSocialNetwork();

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
    Map<String, String> getUserInfoByName(Object user);

    /**
     * get a user friends list by username or user id
     * @param user given username or id
     * @return a list contains specific user's friends information
     */
    List<UserNode> getFriends(Object user);

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
     */
    void addUser(String name, String info);

    /**
     * add a friend pair to this social database
     * @param username1 the name of a friend
     * @param username2 the name of another friend
     */
    void addConnection(String username1, String username2);

    /**
     * @return a collection of all user's name and their associate id
     */
    Map<String, Integer> getAllUser();

    /**
     * print out a specific user's information
     * @param user given username or id
     */
    void printUserInfo(Object user);

}
