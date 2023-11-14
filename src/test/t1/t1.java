package test.t1;
import java.util.*;

public class t1 {
    public static void main(String[] args) {
//
//        Iterator<String> iter = list.iterator();
//        while (iter.hasNext()) {
//            System.out.println(iter.next());
//        }
    }
}


abstract class Mammal {
    abstract public int noLegs();
}

abstract class Lion extends Mammal {
    public int noLegs() { return 4; };
}