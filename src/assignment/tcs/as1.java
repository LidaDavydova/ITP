package assignment.tcs;
import java.util.*;

public class as1 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        ArrayList<String> alph = new ArrayList<>(n);
        Map<String, Integer> alphCount = new HashMap<>(n);
        for (int i=0; i<n; i++) {
            alph.add(in.next());
            alphCount.put(alph.get(i), 0);
        }
        int m = in.nextInt();
        int k = in.nextInt();
        int l = in.nextInt();

        ArrayList<String> type3 = product(alph, alphCount, 1000, 0);
        for (String word : type3) {
            System.out.println(word);
        }
    }
    public static ArrayList<String> product(ArrayList<String> arr, Map<String, Integer> alphCount, int n, int FSAType) {
        int repeat = 1000;
        ArrayList<String> iterRes = new ArrayList<>(List.of(""));
        ArrayList<String> res = new ArrayList<>();
        int l = 0;
        while (repeat > 0) {
            repeat--;
            ArrayList<String> k = new ArrayList<>();
            System.out.println(l);
            for (String x : iterRes) {
                for (int i=0; i<x.length(); i++) {
                    alphCount.put(String.valueOf(x.charAt(i)), alphCount.get(String.valueOf(x.charAt(i)))+1);
                }
                for (String y : arr) {
                    if (l == n) {
                        return res;
                    }
                    int f = 0;
                    for (String symb : arr) {
                        if (alphCount.get(symb) == 0 && !symb.equals(y)) {
                            f++;
                        }
                    }
                    if (f == 1) {
                        l++;
                        res.add(x+y);
                    }
                    k.add(x+y);
                }
                for (int i=0; i<x.length(); i++) {
                    alphCount.put(String.valueOf(x.charAt(i)), 0);
                }
            }
            iterRes = k;
        }
        return res;
    }
}
