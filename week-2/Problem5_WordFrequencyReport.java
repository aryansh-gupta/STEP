import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Problem5_WordFrequencyReport {
    public static void printFilteredWordFrequency(String feedback) {
        String[] stopWords = {"the", "was", "and", "a", "is", "of", "in"};
        String cleaned = feedback.toLowerCase()
                .replace(".", "")
                .replace(",", "");
        String[] words = cleaned.split("\s+");
        HashMap<String, Integer> frequency = new HashMap<>();
        for (String word : words) {
            boolean isStopWord = false;
            for (String stopWord : stopWords) {
                if (word.equals(stopWord)) {
                    isStopWord = true;
                    break;
                }
            }
            if (!isStopWord && !word.isEmpty()) {
                frequency.put(word, frequency.getOrDefault(word, 0) + 1);
            }
        }
        List<Map.Entry<String, Integer>> entries =
                new ArrayList<>(frequency.entrySet());
        entries.sort((a, b) ->
                Integer.compare(b.getValue(), a.getValue()));
        for (Map.Entry<String, Integer> entry : entries) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
    public static void main(String[] args) {
        printFilteredWordFrequency(
            "The mentor was great, the session was great and clear."
        );
    }
}
