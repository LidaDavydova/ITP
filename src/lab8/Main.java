package lab8;

public class Main {
    public static void main(String[] args) {
        Animal animal = new Animal("any", 10, 10, "black");
        Cow cow = new Cow("cow", 10, 10, "black");
        animal.eat();
        cow.eat();
        Cat cat = new Cat("cat");
        cat.eat();
    }
}
