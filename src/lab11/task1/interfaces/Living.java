package lab11.task1.interfaces;

public interface Living {
    default void live() {
        System.out.println(this.getClass().getSimpleName());
    }
}
