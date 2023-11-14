package lab12;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        try (FileInputStream in = new FileInputStream("/home/hedg2/IdeaProjects/test/src/lab12/input.txt");
             FileOutputStream out = new FileOutputStream("/home/hedg2/IdeaProjects/test/src/lab12/output.txt"))
        {
            byte[] buff = new byte[in.available()];
            in.read(buff, 0, buff.length);
            out.write(buff, 0, buff.length);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
