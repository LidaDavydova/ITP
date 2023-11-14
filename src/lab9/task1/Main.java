package lab9.task1;

import java.util.Scanner;

//import static jdk.internal.org.jline.utils.AttributedStringBuilder.append;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        StringBuilder s = new StringBuilder();
        int flag = 1;

        while (flag == 1) {
            int cmd = in.nextInt();
            switch (cmd) {
                case (0):
                    flag = 0;
                    break;
                case (1):
                    System.out.println(s);
                    break;
                case (2):
                    s.append(in.next());
                    break;
                case (3):
                    s.insert(in.nextInt(), in.next());
                    break;
                case (4):
                    s.reverse();
                case(5):
                    s.delete(in.nextInt(), in.nextInt());
                case (6):
                    s.replace(in.nextInt(),in.nextInt(), in.next());
            }
        }
    }
}
