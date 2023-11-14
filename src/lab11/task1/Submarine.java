package lab11.task1;

import lab11.task1.interfaces.Swimmable;

public class Submarine implements Swimmable {
    boolean doing = false;
    public Submarine(boolean doing) {
        this.doing = doing;
    }
    @Override
    public void swim() {
        System.out.println("swimming");
    }
    @Override
    public void stopSwimming() {
        if (doing) {
            System.out.println("not swimming");
        }
    }
}
