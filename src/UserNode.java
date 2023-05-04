import java.util.Objects;

public class UserNode implements IUserNode{
    private String username;
    private int userId;

    private int hashCodeFlag;

    /**
     * initialize a user node with given name and unique id
     * @param name the given username
     * @param id the given user id
     */
    public UserNode(String name, int id, int flag) {
        username = name;
        userId = id;
        hashCodeFlag = flag;
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
                && (!(username == null && userId  == 0))
                && (Objects.equals(username, user.getName()) || user.getName() == null || username == null)
                && (userId == user.getID() || user.getID()  == 0 || userId == 0);
    }

    /**
     * @return userNode hash code
     */
    public int hashCode(){

        switch(hashCodeFlag)
        {
            case 0:
                // use userID as hash code
                return userId;
            case 1 :
                // use username as hash code
                return username.hashCode();

            case 2 :
                // use first four digit of username as hash code
                return username.substring(0,Math.min(username.length(), 4)).hashCode();

            case 3:
                // use first four digit of username and user id as hash code
                return userId + username.substring(0,Math.min(username.length(), 4)).hashCode();
        }

        return System.identityHashCode(this); // system default hashcode
    }

}
