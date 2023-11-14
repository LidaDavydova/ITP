package lab11.task2;
import lab8.*;

import java.util.*;

public class Main {
    public static void addAnimal(List<Animal> animals, Animal an) {
        animals.add(an);
    }
    public static void remove(List<Animal> animals, int idx) {
        animals.remove(idx);
    }
    public static List<Animal> display(List<Animal> animals) {
        for (Animal an : animals) {
            System.out.println(an.toString());
        }
        return animals;
    }

    public static void main(String[] args) {
        List<Animal> animals = new ArrayList<>();
        addAnimal(animals, new Cow("cow", 12, 121, "black"));
        addAnimal(animals, new Cat("cat"));

        System.out.println(display(animals));
    }

}
