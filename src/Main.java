public class Main {

    public static void main(String[] args) {
        SocialDatabase database =
                new SocialDatabase("./test/RecommendTestFile/recommendDataSet.txt", 0);

        UserInteractionService UIService = new UserInteractionService();
        UIService.mainMenu(database);
    }

}