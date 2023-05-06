// CIS_22C - George Korolev
// CIS_22C - Ahror Abdulhamidov

import java.sql.SQLOutput;
import java.util.*;

public class UserInteractionService {
    SocialDatabase database;
    Recommend rec;
    ShortestPath sp;

    public void connectFriends(String name) {
        this.sp = new ShortestPath();
        String friend;

        while (true) {
            friend = getString("Enter search name or Exit\n");

            List<String> myFriends = new ArrayList<>();
            Set<UserNode> fSet = database.getFriends(name);
            for (UserNode f : fSet) {
                myFriends.add(f.getName());
            }

            if (friend.equals("Exit")) {
                break;
            } else if(name.equals(friend)) {
                System.out.println("You can not enter yourself! Please enter a valid name.");
            }else if (rec.getThisUserInfo(database, friend).size() == 0) {
                System.out.println("Friend doesn't exist. Please enter a valid name.");
            } else if (myFriends.contains(friend)) {
                    System.out.println(friend + " is already your friend! Get to know someone new!");
            } else {
                break;
            }
        }

        List<Integer> friendConnect  = sp.getShortesetPathDFS(database, name, friend);

        if (friendConnect.size() == 0) {
            System.out.println("I am sorry. There is no way for you to connect to " + friend +".");
            return;
        }

        System.out.print("There are " + (friendConnect.size() -2) +" people between you and " + friend + ".");
        System.out.print(" Inorder to know " + friend +", you should get to know ");

        for (int i = 1; i < friendConnect.size()-1; i++) {
            if (i == friendConnect.size()-2) {
                System.out.print(database.getUser(friendConnect.get(i)).getName());
            } else {
                System.out.print(database.getUser(friendConnect.get(i)).getName() + ",");
            }
        }

        System.out.println(" in sequence! Good luck!");

    }

    public void findNewFriends(String name) {
        List<String> query = new ArrayList<>();
        List<String> unmodifiableList = Arrays.asList("popular", "age", "hobby", "work");
        List<String> wordList = new ArrayList<>(unmodifiableList);
        while(true){
            String word = getString("Enter the tags you want to search for, or Exit to stop." +
                    " Here are the tags you may consider to add "+ wordList+ "\n");
            if (word.isEmpty()) {
                break;
            }
            if (wordList.contains(word)) {
                wordList.remove(word);
                query.add(word);
            } else if (word.equals("Exit")) {
                System.out.println("The tags you choose are " + query +".");
                break;
            } else {
                System.out.println("Cannot recognize command or you have already choose this tag.");
            }
        }
        rec.getNewFriendsInfo(database, name);
        List<String> result = rec.newFriendsSorted(database, query);
        System.out.println("You may want to know them: "+ result + "!");
    }

    public static String getString(String message) {
        Scanner scanner = new Scanner(System.in);

        while(true){
            System.out.println();
            System.out.print(message);

            if (scanner.hasNextLine()) {
                String input = scanner.nextLine();

                if (input != null)
                    return input;
            }
            System.out.println("An error has occurred, try again.");
        }
    }

    public void mainMenu(SocialDatabase database){
        // Load database
        this.database = database;
        this.rec = new Recommend();
        Map<String, String> info = rec.getThisUserInfo(database, "A");

        System.out.println("Welcome to Find Friends Social Network");
        System.out.println("There are already some profiles stored in the FriendBook that were loaded from serialized files.");
        System.out.println("Once you Exit, the modified hashedDictionaries will be displayed and reserialized (saved).");

        boolean control = true;
        while(control) {
            switch (getString("Enter Login or Exit\n")) {
                case "Login":
                    logIn(database);
                    control = true;
                    break;
                case "Exit":
                    control = false;
                    System.out.println("Thank you");
                    break;
                default:
                    System.out.println("Cannot recognize command.");
                    control = true;
            }
        }
    }

    public void logIn(SocialDatabase database) {
        String username = getString("Enter your username: ");
        Map<String, String> info = rec.getThisUserInfo(database, username);

        if (info.size() != 0) {
            // user profile
            System.out.println("Here Is The User Profile :)");
            System.out.println("User Name: " + username);
            System.out.println("User Age: " + info.get("age"));
            System.out.println("Hobby: " + info.get("hobby"));
            System.out.println("Friends: " + info.get("friend"));

            boolean control = true;
            while(control) {
                System.out.println();
                System.out.println("Hello "+ username+"! What do you want to do today?");
                System.out.println("1. Some one you may know");
                System.out.println("2. Search friends and find how to connect them");
                System.out.println("3. I want to log out :(");
                switch (getString("Enter choice 1, 2 or 3: \n" )) {
                    case "1":
                        findNewFriends(username);
                        control = true;
                        break;
                    case "2":
                        connectFriends(username);
                        control = true;
                        break;
                    case "3":
                        control = false;
                        System.out.println("Logging out...");
                        break;
                    default:
                        System.out.println("Cannot recognize command.");
                        control = true;
                }
            }

        } else System.out.println("Incorrect username.");
    }
}
