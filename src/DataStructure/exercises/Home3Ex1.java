package DataStructure.exercises;
// Lidia Davydova

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Home3Ex1 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        Map<String, Integer> map = new HashMap<>(0, 1000000);
        while (n > 0) {
            n--;
            String word = in.next();
            if (map.get(word) != null) {
                map.put(word, map.get(word)+1);
            } else {
                map.put(word, 1);
            }
        }
        List<Entry<String, Integer>> res = sort(map.entrySet());
        for (Entry<String, Integer> entry : res) {
            System.out.println(entry.getKey() + " " + entry.getVal());
        }
    }
    public static List<Entry<String, Integer>> sort(List<Entry<String, Integer>> list) {
        for (int i=0; i < list.size()-1; i++) {
            for (int j=i+1; j < list.size(); j++) {
                Entry<String, Integer> el2 = list.get(j);
                Entry<String, Integer> el = list.get(i);
                if (el.getVal() < el2.getVal() || (el.getVal().equals(el2.getVal()) && el.getKey().compareTo(el2.getKey()) > 0)) {
                    Collections.swap(list, i, j);
                }
            }
        }
        return list;
    }
}