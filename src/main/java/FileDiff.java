import java.io.*;
import java.util.*;

public class FileDiff {

    private List<String> common;
    private List<String> fileOneDiff;
    private List<String> fileTwoDiff;
    private List<List<String>> list;

    public List<List<String>> compareFiles(File file1, File file2) throws IOException {
        common = new ArrayList<>();
        fileOneDiff = new ArrayList<>();
        fileTwoDiff = new ArrayList<>();
        list = new ArrayList<>();
        BufferedReader bufferedReader1 = new BufferedReader(new FileReader(file1));
        BufferedReader bufferedReader2 = new BufferedReader(new FileReader(file2));
        Map<String, Integer> map = buildMap(bufferedReader1, bufferedReader2);
        return findFileDiff(map);
    }


    private Map<String, Integer> buildMap(final BufferedReader reader1, final BufferedReader reader2) throws IOException {
        Map<String, Integer> map = new HashMap<>();
        String word = reader1.readLine();
        while (word != null) {
            if (map.containsKey(word)) {
                map.put(word, map.get(word) + 1);
            } else map.put(word, 1);
            word = reader1.readLine();
        }

        word = reader2.readLine();
        while (word != null) {
            if (map.containsKey(word)) {
                if (map.get(word) > 0) {
                    common.add(word);
                }
                map.put(word, map.get(word) - 1);
            } else map.put(word, -1);
            word = reader2.readLine();
        }
        return map;
    }

    private List<List<String>> findFileDiff(final Map<String, Integer> map) {
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String word = entry.getKey();
            Integer frequency = entry.getValue();
            if (frequency < 0) {
                while (frequency++ != 0) {
                    fileTwoDiff.add(word);
                }
            } else {
                while (frequency-- != 0) {
                    fileOneDiff.add(word);
                }
            }
        }
        list.add(common);
        list.add(fileOneDiff);
        list.add(fileTwoDiff);
        return list;
    }
}
