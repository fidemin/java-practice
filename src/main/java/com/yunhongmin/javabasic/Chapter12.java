package com.yunhongmin.javabasic;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
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

        NewClass nc = new NewClass();
        nc.oldField = 10;
        System.out.println(nc.getOldField());

        @SuppressWarnings("unchecked")
        ArrayList<NewClass> list = new ArrayList();
        list.add(nc);

        AnnotationEx.run();
        EnumEx.run();
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

class NewClass {
    int newField;

    @Deprecated
    int oldField;

    int getNewField() {
        return this.newField;
    }

    @Deprecated
    int getOldField() {
        return this.oldField;
    }
}

@TestInfo(testedBy="Yunhong Min", testDate=@DateTime(yymmdd="20220101", hhmmss="111111"))
class AnnotationEx {
    static void run() {

        Class<AnnotationEx> cls = AnnotationEx.class;
        TestInfo anno = (TestInfo) cls.getAnnotation(TestInfo.class);
        System.out.println(anno.testedBy());
        System.out.println(anno.testDate().yymmdd());
    }
}

@Retention(RetentionPolicy.RUNTIME)
@interface TestInfo {
    int count() default 1;
    String testedBy();
    TestType testType() default TestType.FIRST;
    String[] testTools() default "JUnit";
    DateTime testDate();
}

@Retention(RetentionPolicy.RUNTIME)
@interface DateTime {
    String yymmdd();
    String hhmmss();
}

enum TestType { FIRST, FINAL }

enum SimpleDirection { EAST, SOUTH, WEST, NORTH}
enum Direction {
    EAST(1, ">") {
        @Override
        public String korean() {
            return "동쪽";
        }
    },
    SOUTH(2, "V") {
        @Override
        public String korean() {
            return "남쪽";
        }
    },
    WEST(3, "<") {
        @Override
        public String korean() {
            return "서쪽";
        }
    }, NORTH(4, "^") {
        @Override
        public String korean() {
            return "북쪽";
        }
    };

    private static final Direction[] DIR_ARR = Direction.values();
    private final int value;
    private final String symbol;

    abstract public String korean();

    Direction(int value, String symbol) {
        this.value = value;
        this.symbol = symbol;
    }

    public Direction rotate(int num) {
        num = num % 4;

        if (num < 0) num += 4;

        return DIR_ARR[(value-1+num) % 4];
    }

    @Override
    public String toString() {
        return name() + "(" + symbol + ")";
    }
}

class EnumEx {
    static void run() {

        SimpleDirection d1 = SimpleDirection.NORTH;
        SimpleDirection d2 = SimpleDirection.WEST;

        switch (d1) {
            case EAST:
                System.out.println("East!"); break;
            case SOUTH:
                System.out.println("South!"); break;
            case WEST:
                System.out.println("West!"); break;
            case NORTH:
                System.out.println("North!"); break;
            default:
                System.out.println("Invalid direction!"); break;
        }

        SimpleDirection[] dArr = SimpleDirection.values();

        for (SimpleDirection d : dArr) {
            System.out.println(d.name() + "=" + d.ordinal());
        }

        Direction d3 = Direction.EAST;
        System.out.println(d3.korean());
        Direction d4 = Direction.WEST;
        System.out.println(d3.rotate(1));
        System.out.println(d3.rotate(2));
        System.out.println(d3.rotate(3));
        System.out.println(d3.rotate(4));
    }
}