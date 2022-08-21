package javabasic;

import java.util.ArrayList;

public class Chapter12 {
    public static void main(String[] args) {
        FruitBox<Fruit> fruitBox = new FruitBox<>();
        FruitBox<Apple> appleBox = new FruitBox<>();

        fruitBox.add(new Apple());
        fruitBox.add(new Grape());
        appleBox.add(new Apple());
        appleBox.add(new Apple());
        // appleBox.add(new Grape());  -> error

        System.out.println(Juicer.makeJuice(fruitBox));
        System.out.println(Juicer.makeJuice(appleBox));
    }
}

class Fruit {
    @Override
    public String toString() { return "Fruit"; }
}

class Apple extends Fruit {
    @Override
    public String toString() { return "Apple"; }
}

class Grape extends Fruit {
    @Override
    public String toString() { return "Grape"; }
}

class FruitBox<T extends Fruit> extends Box<T> {}

class Box<T> {
    ArrayList<T> list = new ArrayList<>();
    void add(T item) { list.add(item); }
    T get(int i) { return list.get(i); }
    ArrayList<T> getList() { return list; }
    int size() { return list.size(); }
    @Override
    public String toString() { return list.toString(); }
}


class Juice {
    String name;
    Juice(String name) { this.name = name; }

    @Override
    public String toString() { return name; }
}

class Juicer {
    static Juice makeJuice(FruitBox<?> box) {
        StringBuffer b = new StringBuffer();

        for (Fruit f : box.getList()) {
            b.append(f.toString());
            b.append(" ");
        }

        return new Juice(b.toString());
    }
}