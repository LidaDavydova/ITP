package test.t1;

// Lidia Davydova
import java.util.ArrayList;
import java.util.Scanner;

public class t1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextInt();

        String[] dict = new String[n];
        scanner.nextLine();
        String[] words = scanner.nextLine().split(" ");
        for (int i = 0; i < n; i++) {
            dict[i] = words[i];
        }
        String text = scanner.nextLine();

        ArrayList<String> res = textSplit(dict, text);
        for (String word : res) {
            System.out.printf("%s", word);
        }
    }

    public static ArrayList<String> textSplit(String[] dict, String text) {
        int n = text.length();
        boolean[] wordExists = new boolean[n+1];
        wordExists[0] = true;

        for (int i=1; i<n+1; i++) {
            for (String word : dict) {
                if (i - word.length() >= 0 && text.substring(i-word.length(), i).equals(word) && wordExists[i-word.length()]) {
                    wordExists[i] = true;
                }
            }
        }
        ArrayList<String> result = new ArrayList<>();
        int idx = n;
        while (idx > 0) {
            for (String word : dict) {
                if (idx - word.length() >= 0 && wordExists[idx-word.length()] && text.substring(idx-word.length(), idx).equals(word)) {
                    result.add(0, word + " ");
                    idx -= word.length();
                    break;
                }
            }
        }
        return result;
    }
}
