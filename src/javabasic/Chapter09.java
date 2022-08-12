package javabasic;

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
