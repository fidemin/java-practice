package javabasic;

import sun.rmi.runtime.NewThreadAction;

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