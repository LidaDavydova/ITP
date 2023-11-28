package lab13;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Media media = new Media();
        media.putObject(new Book("book1"));
        media.putObject(new Video("video1"));
        media.getMedia();
    }
}

class Media<T extends Library> {
    private List<T> list = new ArrayList<T>();
    public Media() {
    }
    public void putObject(T obj) {
        list.add(obj);
    }
    public List<T> getMedia() {
        for (T obj : list){
            System.out.println(obj.getName());
        };
        return list;
    }
}

class Library {
    private String name;
    public Library(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}

class Book extends Library {
    public Book(String name) {
        super(name);
    }
}

class Video extends Library {
    public Video(String name) {
        super(name);
    }
}
