package javabasic;

import java.util.Random;

public class Chapter09 {
    public static void main(String[] args) {
        Point p1 = new Point(0, 1);
        Point p2 = new Point(0, 1);
        System.out.println(p1 == p2);
        System.out.println(p1.equals(p2));

        Circle c1 = new Circle(p1, 3);
        Circle c2 = c1.clone(); // deep copy

        c1.center.x = 10;
        c2.center.y = 20;

        System.out.println(c1.center.equals(c2.center)); // should be false

        Class cObj1 = new Circle(p1, 3).getClass();
        Class cObj2 = Class.class;
        try {

            Class cObj3 = Class.forName("Circle");
        } catch (ClassNotFoundException e) {}

        Integer iObj1 = new Integer(10);
        int i1 = 20;
        int i2 = i1 + iObj1;  // iObj1 is unboxed to int automatically.
        System.out.println(i2);

        Integer iObj2 = i1 + iObj1; // i1 is autoboxing.
        System.out.println(iObj2);

        Random rand = new Random(1); // same seed -> same result
        Random rand2 = new Random(1);

        System.out.println("-------");
        for (int i=0; i<5; i++) {
            System.out.println(rand.nextInt());
        }

        System.out.println("-------");
        for (int i=0; i<5; i++) {
            System.out.println(rand2.nextInt());
        }

        final int record_num = 10;
        final String table_name = "Person";
        final String[] code1 = {"010", "011", "017"};
        final String[] code2 = {"male", "female"};

        for (int i=0; i<record_num; i++) {
            System.out.println("INSERT INTO " + table_name +
                    " VALUES ('" + getRandFromArray(code1)+ "', '" +  getRandFromArray(code2) + "');");
        }
    }

    static String getRandFromArray(String[] arr) {
        Random rand = new Random();
        int pos = rand.nextInt(arr.length);
        return arr[pos];
    }
}

class Point {
    int x;
    int y;

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Point) {
            Point p = (Point) obj;
            return (x == p.x) && (y == p.y);
        }
        return false;
    }
}

class Circle implements Cloneable {
    Point center;
    int radius;

    Circle(Point center, int radius) {
        this.center = center;
        this.radius = radius;
    }

    @Override
    public Circle clone() {
        Object obj = null;
        try {
            obj = super.clone();
        } catch (CloneNotSupportedException e) {}

        Circle c = (Circle) obj;
        c.center = new Point(c.center.x, c.center.y);
        return c;
    }
}
