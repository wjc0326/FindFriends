import java.util.*;

public class Recommend implements IRecommend {

    private Map<String, String> thisUserInfo = new HashMap<>();
    private List<String> allUserName = new ArrayList<>();
    private List<String> friendsName = new ArrayList<>();
    private List<String> newFriends = new ArrayList<>();
    private Map<String, Map<String, String>> newFriendsInfo = new HashMap<>();

    @Override
    public Map<String, String> getThisUserInfo(SocialDatabase database, String username) {
        thisUserInfo = database.getUserInfo(username);
        return thisUserInfo;
    }

    @Override
    public List<String> getNewFriends(SocialDatabase database, String username) {
        // get all usernames
        Map<String, Integer> allUser = database.getAllUser();
        for (Map.Entry<String, Integer> entry : allUser.entrySet()) {
            allUserName.add(entry.getKey());
        }

        // get friends' names
        Set<UserNode> friends = database.getFriends(username);
        for (UserNode friend : friends) {
            friendsName.add(friend.getName());
        }

        // get new friends' names (in all usernames but not in friends' names)
        for (String name : allUserName) {
            if ((!friendsName.contains(name)) && (!name.equals(username))) {
                newFriends.add(name);
            }
        }

        return newFriends;
    }

    @Override
    public Map<String, Map<String, String>> getNewFriendsInfo(SocialDatabase database, String username) {
        thisUserInfo = getThisUserInfo(database, username);
        newFriends = getNewFriends(database, username);
        for (String friend : newFriends) {
            newFriendsInfo.put(friend, database.getUserInfo(friend));
        }

        return newFriendsInfo;
    }

    @Override
    public Map<String, Double> hobbyScore() {
        Double score;

        Map<String, Double> scoreForHobby = new HashMap<>();
        // split by "," and remove the leading and trailing white spaces
        List<String> myHobbies = Arrays.asList(thisUserInfo.get("hobby").split(","));
        List<String> updatedMyHobbies = new ArrayList<>();
        for (String hobby : myHobbies) {
            updatedMyHobbies.add(hobby.strip());
        }

        // STRATEGY: score = num of hobbies in common / total num of this user's hobbies
        for (Map.Entry<String, Map<String, String>> friend : newFriendsInfo.entrySet()) {
            int sameHobby = 0;
            // split by "," and remove the leading and trailing white spaces
            List<String> friendHobbies = Arrays.asList(friend.getValue().get("hobby").split(","));
            List<String> updatedFriendHobbies = new ArrayList<>();
            for (String hobby : friendHobbies) {
                updatedFriendHobbies.add(hobby.strip());
            }

            // iterate through the hobbies of this user and see how many hobbies are in common
            for (String hobby : updatedMyHobbies) {
                if (updatedFriendHobbies.contains(hobby)) {
                    sameHobby++;
                }
            }
            score = (double) sameHobby / updatedMyHobbies.size();
            scoreForHobby.put(friend.getKey(), score);
        }

        return scoreForHobby;
    }

    @Override
    public Map<String, Double> workScore() {
        Double score;

        Map<String, Double> scoreForWork = new HashMap<>();
        String myWork = thisUserInfo.get("work");

        // STRATEGY: score = 1.0 if same work; 0.0 otherwise
        for (Map.Entry<String, Map<String, String>> friend : newFriendsInfo.entrySet()) {
            String friendWork = friend.getValue().get("work");

            if (myWork.equals(friendWork)) {
                score = 1.0;
            } else {
                score = 0.0;
            }

            scoreForWork.put(friend.getKey(), score);
        }

        return scoreForWork;
    }

    @Override
    public Map<String, Double> ageScore() {
        Double score;

        Map<String, Double> scoreForAge = new HashMap<>();
        int myAge = Integer.parseInt(thisUserInfo.get("age"));

        // STRATEGY: score = 1 - (Δ(age) / this user's age)
        for (Map.Entry<String, Map<String, String>> friend : newFriendsInfo.entrySet()) {
            int friendAge = Integer.parseInt(friend.getValue().get("age"));

            score = 1.0 - ((double)Math.abs(myAge - friendAge) / (double)myAge);
            scoreForAge.put(friend.getKey(), score);
        }

        return scoreForAge;
    }

    @Override
    public Map<String, Double> popularityScore(SocialDatabase database) {
        Double score;
        int allConnections = database.connectionNum();

        Map<String, Double> scoreForPopularity = new HashMap<>();

        // STRATEGY: score = connection of the new friend / total num of connections
        for (Map.Entry<String, Map<String, String>> friend : newFriendsInfo.entrySet()) {
            String friendName = friend.getKey();
            int connection = database.getFriends(friendName).size();

            score = (double)connection / (double)allConnections;
            scoreForPopularity.put(friend.getKey(), score);
        }

        return scoreForPopularity;
    }

    public static Comparator<Map.Entry<String, Double>> byReverseScoreOrder() {
        return new Comparator<Map.Entry<String, Double>>() {
            public int compare(Map.Entry<String, Double> e1, Map.Entry<String, Double> e2) {
                return -(e1.getValue().compareTo(e2.getValue()));
            }
        };
    }

    public Map<String, Double> switchCases(SocialDatabase database, String word) {
        Map<String, Double> scoreMap = new HashMap<>();
        switch (word) {
            case "hobby":
                scoreMap = hobbyScore();
                break;
            case "work":
                scoreMap = workScore();
                break;
            case "age":
                scoreMap = ageScore();
                break;
            case "popular":
                scoreMap = popularityScore(database);
                break;
            default:
                System
                        .out.println("Invalid standard!");
        }
        return scoreMap;
    }

    public List<String> oneWordScore(SocialDatabase database, String word) {
        Map<String, Double> scoreMap = switchCases(database, word);
        List<String> result = new ArrayList<>();

        List<Map.Entry<String, Double>> scoreList = new ArrayList<>(scoreMap.entrySet());
        // call the sort() method of Collections
        Collections.sort(scoreList, byReverseScoreOrder());
        // get entry from list to the map
        for (Map.Entry<String, Double> entry : scoreList) {
            result.add(entry.getKey());
        }

        return result;
    }

    @Override
    public List<String> twoWordsScore(SocialDatabase database, List<String> wordList) {
        Map<String, Double> twoWordsScoreMap = new HashMap<>();
        List<String> result = new ArrayList<>();

        Set<String> wordSet = new HashSet<>();
        wordSet.addAll(wordList);

        // check whether the wordList is valid when passed in this function
        // 1. the num of words in wordList is two
        // 2. no duplicate
        if ((wordList.size() != 2) || (wordSet.size() != 2)) {
            return result;
        }

        String firstWord = wordList.get(0);
        String secondWord = wordList.get(1);
        Map<String, Double> firstScoreMap = switchCases(database, firstWord);
        Map<String, Double> secondScoreMap = switchCases(database, secondWord);

        // STRATEGY: combined score = first standard * 0.7 + second standard * 0.3
        for (Map.Entry<String, Double> entry : firstScoreMap.entrySet()) {
            String friend = entry.getKey();
            Double firstScore = entry.getValue();
            Double secondScore = secondScoreMap.get(friend);
            Double combinedScore = firstScore * 0.7 + secondScore * 0.3;
            twoWordsScoreMap.put(friend, combinedScore);
        }

        List<Map.Entry<String, Double>> scoreList = new ArrayList<>(twoWordsScoreMap.entrySet());
        // call the sort() method of Collections
        Collections.sort(scoreList, byReverseScoreOrder());
        // get entry from list to the map
        for (Map.Entry<String, Double> entry : scoreList) {
            result.add(entry.getKey());
        }

        return result;
    }

    @Override
    public List<String> threeWordsScore(SocialDatabase database, List<String> wordList) {
        Map<String, Double> threeWordsScoreMap = new HashMap<>();
        List<String> result = new ArrayList<>();

        Set<String> wordSet = new HashSet<>();
        wordSet.addAll(wordList);

        // check whether the wordList is valid when passed in this function
        // 1. the num of words in wordList is two
        // 2. no duplicate
        if ((wordList.size() != 3) || (wordSet.size() != 3)) {
            return result;
        }

        String firstWord = wordList.get(0);
        String secondWord = wordList.get(1);
        String thirdWord = wordList.get(2);
        Map<String, Double> firstScoreMap = switchCases(database, firstWord);
        Map<String, Double> secondScoreMap = switchCases(database, secondWord);
        Map<String, Double> thirdScoreMap = switchCases(database, thirdWord);

        // STRATEGY: combined score = first * 0.5 + second * 0.3 + third * 0.2
        for (Map.Entry<String, Double> entry : firstScoreMap.entrySet()) {
            String friend = entry.getKey();
            Double firstScore = entry.getValue();
            Double secondScore = secondScoreMap.get(friend);
            Double thirdScore = thirdScoreMap.get(friend);
            Double combinedScore = firstScore * 0.5 + secondScore * 0.3 + thirdScore * 0.2;
            threeWordsScoreMap.put(friend, combinedScore);
        }

        List<Map.Entry<String, Double>> scoreList = new ArrayList<>(threeWordsScoreMap.entrySet());
        // call the sort() method of Collections
        Collections.sort(scoreList, byReverseScoreOrder());
        // get entry from list to the map
        for (Map.Entry<String, Double> entry : scoreList) {
            result.add(entry.getKey());
        }

        return result;
    }

    @Override
    public List<String> fourWordsScore(SocialDatabase database, List<String> wordList) {
        Map<String, Double> fourWordsScoreMap = new HashMap<>();
        List<String> result = new ArrayList<>();

        Set<String> wordSet = new HashSet<>();
        wordSet.addAll(wordList);

        // check whether the wordList is valid when passed in this function
        // 1. the num of words in wordList is two
        // 2. no duplicate
        if ((wordList.size() != 4) || (wordSet.size() != 4)) {
            return result;
        }

        String firstWord = wordList.get(0);
        String secondWord = wordList.get(1);
        String thirdWord = wordList.get(2);
        String forthWord = wordList.get(3);

        Map<String, Double> firstScoreMap = switchCases(database, firstWord);
        Map<String, Double> secondScoreMap = switchCases(database, secondWord);
        Map<String, Double> thirdScoreMap = switchCases(database, thirdWord);
        Map<String, Double> forthScoreMap = switchCases(database, forthWord);

        // STRATEGY: combined score = first * 0.5 + second * 0.3 + third * 0.2
        for (Map.Entry<String, Double> entry : firstScoreMap.entrySet()) {
            String friend = entry.getKey();
            Double firstScore = entry.getValue();
            Double secondScore = secondScoreMap.get(friend);
            Double thirdScore = thirdScoreMap.get(friend);
            Double forthScore = forthScoreMap.get(friend);

            Double combinedScore = firstScore * 0.5 + secondScore * 0.2 +
                                   thirdScore * 0.2 + forthScore * 0.1;
            fourWordsScoreMap.put(friend, combinedScore);
        }

        List<Map.Entry<String, Double>> scoreList = new ArrayList<>(fourWordsScoreMap.entrySet());
        // call the sort() method of Collections
        Collections.sort(scoreList, byReverseScoreOrder());
        // get entry from list to the map
        for (Map.Entry<String, Double> entry : scoreList) {
            result.add(entry.getKey());
        }

        return result;
    }

    @Override
    public List<String> newFriendsSorted(SocialDatabase database, List<String> wordList) {
        List<String> nameSorted = new ArrayList<>();

        int length = wordList.size();
        switch (length) {
            case 1:
                nameSorted = oneWordScore(database, wordList.get(0));
                break;
            case 2:
                nameSorted = twoWordsScore(database, wordList);
                break;
            case 3:
                nameSorted = threeWordsScore(database, wordList);
                break;
            case 4:
                nameSorted = fourWordsScore(database, wordList);
                break;
            default:
                // the user doesn't input any standards
                // we use the default value: [hobby, age, work, popular]
                wordList = Arrays.asList("hobby", "age", "work", "popular");
                nameSorted = fourWordsScore(database, wordList);
        }

        return nameSorted;
    }
}
