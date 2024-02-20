package assignment.tcs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Task2 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int Q = in.nextInt();
        int S = in.nextInt();
        int F = in.nextInt();
        int N = in.nextInt();
        String stState = "";

        HashMap<String, ArrayList<String[]>> func = new HashMap<>(Q*S);
        List<String> states = new ArrayList<>(Q);
        List<String> alph = new ArrayList<>(S);
        List<String> acceptedStates = new ArrayList<>(F);
        for (int i=0; i<Q; i++) {
            states.add(in.next());
        }
        for (int i=0; i<S; i++) {
            alph.add(in.next());
        }
        for (int i=0; i<S*Q; i++) {
            String q1 = in.next();
            String input = in.next();
            String q2 = in.next();
            if (func.get(input) == null) {
                ArrayList<String[]> arr = new ArrayList<>();
                arr.add(new String[]{q1, q2});
                func.put(input, arr);
            } else {
                ArrayList<String[]> arr = func.get(input);
                arr.add(new String[]{q1, q2});
                func.put(input, arr);
            }
        }
        stState = in.next();
        for (int i=0; i<F; i++) {
            acceptedStates.add(in.next());
        }

        String word;
        for (int i=0; i<N; i++) {
            String initialState = stState;
            word = in.next();
            int k = 0;
            if (word.equals("_")) {
                for (String state : acceptedStates) {
                    if (stState.equals(state)) {
                        System.out.printf("%s ", "A");
                        k = 1;
                        break;
                    }
                }
                if (k == 0) {
                    System.out.printf("%s ", "R");
                }
                continue;
            }
            int f = 0;
            for (int j=0; j<word.length(); j++) {
                if (func.get(String.valueOf(word.charAt(j))) != null) {
                    for (String[] transFunc : func.get(String.valueOf(word.charAt(j)))) {
                        if (initialState.equals(transFunc[0])) {
                            initialState = transFunc[1];
                            f = 1;
                            break;
                        }
                    }
                }
                if (f == 0) {
                    break;
                }
            }
            k = 0;
            if (f == 0) {
                System.out.printf("%s ", "R");
                continue;
            }
            for (String state : acceptedStates) {
                if (initialState.equals(state)) {
                    System.out.printf("%s ", "A");
                    k = 1;
                    break;
                }
            }
            if (k == 0) {
                System.out.printf("%s ", "R");
            }
        }
    }
}
