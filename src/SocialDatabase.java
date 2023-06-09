import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class SocialDatabase implements ISocialDatabase {
    private SocialGraph socialNetwork = new SocialGraph();  // the graph of social network
    // database contains all users info
    private Map<UserNode, Map<String, String>> userDatabase = new HashMap<>();
    private int userNum = 0;  // the number of user in this database
    private Set<UserNode> userSet = new HashSet<>();  // a set stored all the users
    private int hashCodeFlag;  // a flag indicate what type of hash code is using
    private long usedTime; // time used to create the database

    /**
     * Initialize the database with input file
     * @param filename the name of a file contains the user data infomation
     * @param flag a flag indicate what type of hash code is using
     *             0: use user id as hash code
     *             1: use username as hash code
     *             2: use first four digit of username as hash code
     *             3: use first four digit of username and user id as hash code
     *             other: system default hash code
     */
    public SocialDatabase(String filename, int flag) {
        hashCodeFlag = flag;
        long startTime =  System.currentTimeMillis();
        createDataBase(filename);  // first create database
        createSocialNetwork();  // then create social network
        long endTime =  System.currentTimeMillis();
        usedTime = endTime - startTime;
    }

    /**
     * use to create the HashMap store the user info with input file
     * @param filename the name of a file contains the user data information
     */
    private void createDataBase(String filename) {

        try {
            // read in file
            File dataFile = new File(filename);
            Scanner reader = new Scanner(dataFile);
            while (reader.hasNextLine()) {
                // get next user info line
                String data = reader.nextLine();
                // get username
                String[] nameInfo = data.split(";",3);
                // add user
                addUserFromFile(nameInfo[0].trim(), Integer.parseInt(nameInfo[1].trim()),
                        nameInfo[2]);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void addUserFromFile(String name, int id, String info) {
        // create a new user
        UserNode newUser = new UserNode(name, id,hashCodeFlag);
        if (userSet.contains(newUser)) {
            // if username exist
            return;
        }
        // update userNum
        userNum ++;
        // add newUser into user set and social network
        userSet.add(newUser);
        socialNetwork.addVertex(newUser);

        // split info string to get and store user information
        Map<String, String> infoMap = new LinkedHashMap<>();  // create a map to store the info
        String[] singleInfo = info.split(";");  // split info into categories
        for (String s: singleInfo) {
            String[] keyValue = s.split(":");
            String value = "";
            if (keyValue.length != 1) {
                value = keyValue[1].trim();
            }
            String key = keyValue[0].trim().toLowerCase();
            if (!key.equals("friend")) {
                value = value.toLowerCase();
            }
            infoMap.put(key, value);
        }

        userDatabase.put(newUser, infoMap);  // store the user into database

    }

    /**
     * use the stored user information create a social network graph
     */
    private void createSocialNetwork() {
        for (UserNode u: userSet) {
            // go through all users in userSet
            // get friends
            String friendString = userDatabase.get(u).get("friend");
            String[] friends = friendString.split(",");

            for (String s: friends) {
                // go through each possible friend and add connection
                s = s.trim();
                addConnection(u.getName(), s);
            }
        }

    }

    @Override
    public UserNode getUser(Object user) {
        // check the type of the object and create a temp userNode with it
        UserNode temp;
        if (user instanceof String) {
            temp = new UserNode((String) user, 0, hashCodeFlag);
        } else if (user instanceof Integer) {
            temp = new UserNode(null, (int) user, hashCodeFlag);
        } else {
            temp = new UserNode(null, 0, hashCodeFlag);
        }

        // go through the userSet return the wanted node
        for (UserNode u: userSet) {
            if (u.equals(temp)) {
                return u;
            }
        }

        return null;
    }

    @Override
    public Map<String, String> getUserInfo(Object user) {
        UserNode userNode = getUser(user);  // get user Node
        if (userNode == null) {
            return new HashMap<>(); // if user not exit, return an empty set
        }
        return userDatabase.get(userNode);
    }

    @Override
    public Set<UserNode> getFriends(Object user) {
        UserNode userNode = getUser(user);  // get user Node
        if (userNode == null) {
            return new HashSet<>(); // if user not exit, return an empty set
        }
        return socialNetwork.getAdj(userNode);
    }

    @Override
    public int userNum() {
        return userNum;
    }

    @Override
    public int connectionNum() {
        return socialNetwork.edgeNum();
    }

    @Override
    public boolean addUser(String name, String info) {
        if (getUser(name) != null) {
            // if username exist
            return false;
        }

        // create a new user
        UserNode newUser = new UserNode(name, 0, hashCodeFlag);

        // update userNum
        userNum ++;
        // assign id for user
        newUser.setID(userNum);
        // add newUser into user set and social network
        userSet.add(newUser);
        socialNetwork.addVertex(newUser);

        // split info string to get and store user information
        Map<String, String> infoMap = new LinkedHashMap<>();  // create a map to store the info
        String[] singleInfo = info.split(";");  // split info into categories
        for (String s: singleInfo) {
            String[] keyValue = s.split(":");
            String value = "";
            if (keyValue.length != 1) {
                value = keyValue[1].trim();
            }
            String key = keyValue[0].trim().toLowerCase();
            if (!key.equals("friend")) {
                value = value.toLowerCase();
            } else {
                // get friends
                String[] friends = value.split(",");
                for (String f: friends) {
                    // go through each possible friend and add connection
                    f = f.trim();
                    addConnection(name, f);
                }
            }
            infoMap.put(key, value);
        }

        userDatabase.put(newUser, infoMap);  // store the user into database

        return true;
    }

    @Override
    public void addConnection(Object user1, Object user2) {
        // get userNode
        UserNode userNode1 = getUser(user1);
        UserNode userNode2 = getUser(user2);

        if (userNode1 != null && userNode2 != null) {
            // if both user exist
            if (!userNode1.equals(userNode2)) {
                // if not the same node
                socialNetwork.addEdge(userNode1, userNode2);
            }
        }
    }

    @Override
    public Map<String, Integer> getAllUser() {
        Map<String, Integer> result = new HashMap<>();
        for (UserNode u: userSet) {
            result.put(u.getName(), u.getID());
        }
        return result;
    }

    @Override
    public void printUserInfo(Object user) {
        // get userNode
        UserNode userNode = getUser(user);
        if (userNode == null) {
            System.out
                    .println("No Such User");
            return;
        }
        userNode.printUser();
        // get user info
        Map<String, String> userInfo = userDatabase.get(userNode);
        for (Map.Entry<String, String> info: userInfo.entrySet()) {
            if (!Objects.equals(info.getKey(), "friend")) {
                System.out
                        .println(info.getKey() + ": " + info.getValue());
            }
        }
        // get user friends
        Set<UserNode> friends = socialNetwork.getAdj(userNode);
        System.out
                .print("friend: ");
        for (UserNode u: friends) {
            System.out
                    .print(u.getName() + ";  ");
        }
        System.out
                .println("\n");
    }

    @Override
    public void writeToFile(String filename) {
        FileWriter writer;
        try {
            writer = new FileWriter(filename);

            for (UserNode u: userSet) {
                StringBuilder s = new StringBuilder(u.getName() + ";" + u.getID() + ";");

                // get user info
                Map<String, String> userInfo = userDatabase.get(u);
                for (Map.Entry<String, String> info: userInfo.entrySet()) {
                    if (!Objects.equals(info.getKey(), "friend")) {
                        s.append(info.getKey()).append(": ").append(info.getValue()).append("; ");
                    }
                }

                // get user friends
                Set<UserNode> friends = socialNetwork.getAdj(u);
                s.append("friend: ");
                for (UserNode f: friends) {
                    s.append(f.getName()).append(", ");
                }
                s.append("\n");

                writer.write(s.toString());
            }

            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public long getTime() {
        return usedTime;
    }


}
