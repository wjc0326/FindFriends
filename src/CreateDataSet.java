import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class CreateDataSet {
    // 30 different possible works
    String[] work = {"Software Engineer","Data Scientist","UX Designer","Product Manager",
                     "Project Manager","Marketing Manager","Sales Manager","HR Manager",
                     "Financial Analyst","Investment Banker","Lawyer","Doctor","Nurse",
                     "Teacher","Professor","Research Scientist","Mechanical Engineer",
                     "Electrical Engineer","Civil Engineer","Architect","Graphic Designer",
                     "Copywriter","Journalist", "Editor","Public Relations Specialist",
                     "Social Media Manager","Content Creator","Entrepreneur", "Consultant",
                     "Executive Assistant"};

    // 30 different possible hobbies
    String[] hobby = {"Reading","Writing","Drawing","Painting","Sculpting","Singing",
                      "Dancing","Acting","Playing an instrument","Composing music","Cooking",
                      "Baking","Gardening","Knitting","Crocheting","Sewing","Woodworking",
                      "Metalworking", "Jewelry making","Pottery","Calligraphy","Photography",
                      "Film making","Video editing","Gaming","Programming","Astronomy","Hiking",
                      "Camping","Traveling"};

    List<String> name = new ArrayList<>();

    /**
     * create a random name array with given size
     * @param dataNum given name array size
     */
    private void randomNameArray(int dataNum) {
        int count = 0;

        while (count < dataNum) {
            // random generate a length of the name between 5 and 10
            Random random = new Random();
            int length = random.nextInt(6) + 5;
            StringBuilder newname = new StringBuilder();
            for (int i = 0; i < length; i++) {
                if (i == 0) {
                    newname.append((char) (random.nextInt(26) + 'A'));
                    continue;
                }
                newname.append((char) (random.nextInt(26) + 'a'));
            }
            if (!name.contains(String.valueOf(newname))) {
                // if name not exist, add into list
                name.add(String.valueOf(newname));
                count++;
            }
        }

    }

    public void writeToFile(String filename, int dataNum) {

        // randomly generate a name array
        randomNameArray(dataNum);
        Random random = new Random();

        FileWriter writer;
        try {
            writer = new FileWriter(filename);

            for (int i = 1; i < dataNum + 1; i++) {
                // record name and id
                StringBuilder s = new StringBuilder(name.get(i - 1) + "; " + i + "; ");
                // generate a random age between 18 and 60
                s.append("Age: ").append(random.nextInt(43) + 18).append("; ");
                // generate a random job
                s.append("Work: ").append(work[random.nextInt(30)]).append("; ");
                // generate several random hobby
                s.append("Hobby: ");
                int hobbyNum = random.nextInt(6);
                for (int n = 0; n < hobbyNum; n++) {
                    s.append(hobby[random.nextInt(30)]);
                    if (n != hobbyNum - 1) {
                        s.append(", ");
                    }
                }
                s.append("; ");
                // generate several random friend
                s.append("Friend: ");
                int friendNum = random.nextInt(6);
                for (int n = 0; n < friendNum; n++) {
                    s.append(name.get(random.nextInt(dataNum))).append(", ");
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


}
