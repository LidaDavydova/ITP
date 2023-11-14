package lab11.task1;

import lab11.task1.interfaces.*;

public class Duck implements Swimmable, Flyable, Living {
    @Override
    public void swim() {
        System.out.println("swimming");
    }
    @Override
    public void stopSwimming() {
        System.out.println("not swimming");
    }
    @Override
    public void fly() {
        System.out.println("flying");
    }
    @Override
    public void stopFlying() {
        System.out.println("not flying");
    }
}
