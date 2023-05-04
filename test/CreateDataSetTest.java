import org.junit.Test;

import static org.junit.Assert.*;

public class CreateDataSetTest {

    @Test
    public void writeToFile() {
        CreateDataSet creater = new CreateDataSet();
        creater.writeToFile("./test/DatabaseTestFile/createdDataSet.txt", 5000);
    }
}