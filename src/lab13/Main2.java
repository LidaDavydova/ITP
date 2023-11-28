package lab13;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Main2 {
    public static void main(String[] args) {
        Set<Animal> animals = new HashSet<>();
        animals.add(new Animal("animal1"));
    }
    public static void displayAnimals(Collection<? extends Animal> animals) {
        
    }
}

class Animal {
    private String nickname;
    public Animal(String nickname) {
        this.nickname = nickname;
    }
    public void voice() {
        System.out.println("voice");
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return Objects.equals(nickname, animal.nickname);
    }
}

class Cat extends Animal {
    private int purLoudness;
    public Cat(String nickname, int purLoudness) {
        super(nickname);
        this.purLoudness = purLoudness;
    }
    @Override
    public void voice() {
        System.out.println("myau" + " " + purLoudness);
    }
}

class Dog extends Animal {
    private int barkingLoudness;
    public Dog(String nickname, int barkingLoudness) {
        super(nickname);
        this.barkingLoudness = barkingLoudness;
    }
    @Override
    public void voice() {
        System.out.println("gav" + " " + barkingLoudness);
    }
}