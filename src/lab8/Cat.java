package lab8;

public class Cat extends Animal{
    public Cat(String name) {
        super(name);
    }
    @Override
    public void eat() {
        super.eat();
        System.out.println("Cat");
    }
    @Override
    public void sleep() {
        super.sleep();
    }
    @Override
    public void makeSound() {
        super.makeSound();
    }
}
