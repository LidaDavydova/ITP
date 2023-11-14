package lab8;

public class Cow extends Animal{
    public Cow(String name, double height, double weight, String color) {
        super(name, height, weight, color);
    }

    @Override
    public void eat() {
        super.eat();
        System.out.println("A Cow is eating");
    }
    @Override
    public void sleep() {
        System.out.println("A Cow is sleeping");
    }
    @Override
    public void makeSound() {
        System.out.println("A Cow is makeing sound");
    }
}
