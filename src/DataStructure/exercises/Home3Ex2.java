package DataStructure.exercises;
// Lidia Davydova

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Home3Ex2 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        Map<String, Integer> map = new HashMap<>(0, 100000);
        Map<String, Integer> map2 = new HashMap<>(0, 100000);
        while (n > 0) {
            n--;
            String word = in.next();
            if (map.get(word) == null) {
                map.put(word, 1);
            }
        }
        int m = in.nextInt();
        while (m > 0) {
            m--;
            String word = in.next();
            if (map.get(word) == null) {
                if (map2.get(word) == null) {
                    map2.put(word, 1);
                }
            }
        }
        List<Entry<String, Integer>> res = map2.entrySet();
        System.out.println(res.size());
        for (Entry<String, Integer> word : res) {
            System.out.println(word.getKey());
        }
    }
}

