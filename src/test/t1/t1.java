package test.t1;
import java.util.*;

public class t1 {
    public static void main(String[] args) {
        Box<Integer, List<String>> a = new Box<Integer, List<String>>(1, List.of("fd", "wew"));
        System.out.println(a.getObject());
    }
}


class Box<T, K> {
    private T type1;
    private K type2;
    public Box(T t, K t2) {
        this.type1 = t;
        this.type2 = t2;
    }
    public String getObject() {
        return type1.toString() + type2.toString();
    }
}
