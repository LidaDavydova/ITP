package lab9.task2;

public class Main {
    public static void main(String[] args) {
        Ex2 y = new Ex2();
    }
}

abstract class Ex {
    abstract int area();
}

class Ex2 extends Ex {
    public Ex2() {

    }
    @Override
    int area() {
        return 0;
    }
}