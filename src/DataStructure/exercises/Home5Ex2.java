package DataStructure.exercises;
// Lidia Davydova
import java.util.ArrayList;
import java.util.Scanner;

public class Home5Ex2 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int w = in.nextInt();
        int[][] weights = new int[n][2];
        int[] costs = new int[n];
        for (int i=0; i<n; i++) {
            weights[i][0] = in.nextInt();
            weights[i][1] = i+1;
        }
        for (int i=0; i<n; i++) {
            costs[i] = in.nextInt();
        }

        int[][] table = new int[n+1][w+1];
        ArrayList<Integer> taken = new ArrayList<>(n);

        for (int i=0; i<n+1; i++) {
            for (int j=0; j<w+1; j++) {
                if (i == 0) {
                    table[i][j] = 0;
                } else {
                    if (weights[i-1][0] > j) {
                        table[i][j] = table[i-1][j];
                    } else {
                        table[i][j] = Math.max(table[i-1][j], table[i-1][j-weights[i-1][0]] + costs[i-1]);
                    }
                }
            }
        }


        for (int i=n; i>0; i--) {
            if (table[i][w] != table[i-1][w]) {
                w -= weights[i-1][0];
                if (w < 0) {
                    break;
                }
                taken.add(weights[i-1][1]);
            }
        }

        System.out.println(taken.size());
        for (Integer idx : taken) {
            System.out.printf("%d ", idx);
        }
    }
}
