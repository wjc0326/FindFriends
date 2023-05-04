import java.util.List;
import java.util.Map;

public class SocialDatabase implements ISocialDatabase{
    @Override
    public void init(String filename) {

    }

    @Override
    public void createDataBase(String filename) {

    }

    @Override
    public void createSocialNetwork() {

    }

    @Override
    public UserNode getUser(Object user) {
        return null;
    }

    @Override
    public Map<String, String> getUserInfoByName(Object user) {
        return null;
    }

    @Override
    public List<UserNode> getFriends(Object user) {
        return null;
    }

    @Override
    public int userNum() {
        return 0;
    }

    @Override
    public int connectionNum() {
        return 0;
    }

    @Override
    public void addUser(String name, String info) {

    }

    @Override
    public void addConnection(String username1, String username2) {

    }

    @Override
    public Map<String, Integer> getAllUser() {
        return null;
    }

    @Override
    public void printUserInfo(Object user) {

    }


}
