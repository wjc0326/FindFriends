import java.util.Objects;

public class UserNode implements IUserNode{
    private String username;
    private int userId;

    /**
     * initialize a user node with given name and unique id
     * @param name the given username
     * @param id the given user id
     */
    public UserNode(String name, int id) {
        username = name;
        userId = id;
    }

    @Override
    public String getName() {
        return username;
    }

    @Override
    public int getID() {
        return userId;
    }

    @Override
    public void setName(String name) {
        username = name;
    }

    @Override
    public void setID(int id) {
        userId = id;
    }

    @Override
    public void printUser() {
        System.out.println("Username: " + username + "\nUserID: " + userId);
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof UserNode user)) {
            return false;
        }

        return (!(user.getName() == null && user.getID()  == 0))
                && (Objects.equals(username, user.getName()) || user.getName() == null)
                && (userId == user.getID() || user.getID()  == 0);
    }

    /**
     * @return userNode hash code
     */
    public int hashCode(){
        // use userID as hash code
        return userId;

        // use username as hash code
//        return username.hashCode();

        // use first four digit of username as hash code
//        return username.substring(0,4).hashCode();

        // use first four digit of username and user id as hash code
//        return userId + username.substring(0,4).hashCode();
    }

}
