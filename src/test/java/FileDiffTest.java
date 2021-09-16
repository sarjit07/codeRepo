import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileDiffTest {

    @InjectMocks
    FileDiff fileDiff = new FileDiff();

    private static final String FILE_ONE_PATH = "/src/test/resources/file1.txt";
    private static final String FILE_TWO_PATH = "/src/test/resources/file2.txt";
    private static final String FILE_THREE_PATH = "/src/test/resources/file3.txt";
    private static final String ROOT_PATH = System.getProperty("user.dir");


    @Test
    public void test_compareFiles_success_with_unique_words() throws IOException {
        List<List<String>> list = fileDiff.compareFiles(new File(ROOT_PATH + FILE_ONE_PATH),
                new File(ROOT_PATH + FILE_TWO_PATH));
        Assertions.assertNotNull(list);
        Assertions.assertTrue(list.size() == 3);
        Assertions.assertEquals(list.get(0).size(), 1);
        Assertions.assertEquals(list.get(1).size(), 2);
        Assertions.assertEquals(list.get(2).size(), 2);
        Assertions.assertEquals(list.get(2).get(0), "jkl");
        Assertions.assertEquals(list.get(2).get(1), "mno");
    }

    @Test
    public void test_compareFiles_success_with_duplicates() throws IOException {
        List<List<String>> list = fileDiff.compareFiles(new File(ROOT_PATH + FILE_ONE_PATH),
                new File(ROOT_PATH + FILE_THREE_PATH));
        Assertions.assertNotNull(list);
        Assertions.assertTrue(list.size() == 3);
        Assertions.assertEquals(list.get(0).size(), 2);
        Assertions.assertEquals(list.get(1).size(), 1);
        Assertions.assertEquals(list.get(2).size(), 3);
        Assertions.assertEquals(list.get(2).get(0), "abc");
        Assertions.assertEquals(list.get(2).get(1), "def");
        Assertions.assertEquals(list.get(2).get(2), "jkl");
    }

    @Test
    public void test_compareFiles_fail_with_wrong_file_path() {
        Assertions.assertThrows(IOException.class, () -> fileDiff.compareFiles(new File(ROOT_PATH),
                new File(ROOT_PATH + FILE_TWO_PATH)));
    }
}
