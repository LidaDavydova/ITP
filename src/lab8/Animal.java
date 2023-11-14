package lab8;

public class Animal {
    private String name;
    private double height;
    private double weight;
    private String color;

    public Animal(String name, double height, double weight, String color) {
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.color = color;
    }
    public Animal(String name) {
        this.name = name;
    }

    public void eat() {
        System.out.println("The animal is eating: "+name);
    }
    public void sleep() {
        System.out.println("The animal is sleeping");
    }
    public void makeSound() {
        System.out.println("The animal is makeing sound");
    }
    @Override
    public String toString() {
        return this.name;
    }
}
