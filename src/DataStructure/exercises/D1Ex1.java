// Lidia Davydova

package DataStructure.exercises;

import java.util.Scanner;

public class D1Ex1 {
    public static Player[] sort(Player[] players, int k) {
        int n = players.length;
        // selection sort from biggest score and only for k
        for (int i=n-1; (i>n-k-1 && i>0); i--) {
            int m = i;
            for (int j=i-1; j>=0; j--) {
                if (players[j].getScore() > players[m].getScore()) {
                    m = j;
                }
            }
            // if found biggest score than i th score, then change
            if (i != m) {
                Player t = players[m];
                players[m] = players[i];
                players[i] = t;
            }
        }
        return players;
    }
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int k = in.nextInt();
        Player[] players = new Player[n];
        for (int i=0; i<n; i++) {
            players[i] = new Player(in.next(), in.nextInt()); // parse players data
        }
        players = sort(players, k);
        for (int i=n-1; (i>n-k-1 && i>=0); i--) {
            System.out.println(players[i].getPlayer() + " " + players[i].getScore());
        }
    }
}

class Player {
    private String player;
    private int score;

    public Player(String player, int score) {
        this.player = player;
        this.score = score;
    }

    public String getPlayer() {
        return player;
    }

    public int getScore() {
        return score;
    }
}
