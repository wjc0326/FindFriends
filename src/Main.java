public class Main {

    public static void main(String[] args) {
        SocialDatabase database =
                new SocialDatabase("./test/RecommendTestFile/recommendDataSet.txt", 0);

        UserInteractionService service = new UserInteractionService();
        service.mainMenu(database);
    }

}