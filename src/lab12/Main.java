package lab12;
import java.io.*;
import java.util.Scanner;
import java.text.*;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        try {
            int a = Integer.parseInt(in.nextLine());
            int b = Integer.parseInt(in.nextLine());
            System.out.println(a / b);
        } catch (NumberFormatException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
