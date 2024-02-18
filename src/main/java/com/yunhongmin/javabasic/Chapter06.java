package com.yunhongmin.javabasic;

public class Chapter06 {
    public static void main(String[] args) {
        System.out.println(add(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));

        Car car1 = new Car("blue", "auto", 4);
        System.out.println(car1);
        System.out.println(Car.getNumOfCars());

        Car car2 = new Car("red");
        System.out.println(car2);
        System.out.println(Car.getNumOfCars());
    }

    static int add(int... nums) {
        int result = 0;
        for (int num: nums) {
            result += num;
        }
        return result;
    }

}

class Car {
    // numOfCars initialized to 0: member var
    static int numOfCars;
    static {
        System.out.println("numOfCars: " + numOfCars);
    }

    {
        // called before constructor
        numOfCars++;
        System.out.println("numOfCars: " + numOfCars);
    }

    String color;
    String gearType;
    int numOfDoors;

    Car(String color, String gearType, int numOfdoors) {
        this.color = color;
        this.gearType = gearType;
        this.numOfDoors = numOfdoors;
    }

    Car(String color) {
        this(color, "auto", 4);
    }

    static int getNumOfCars() {
        return numOfCars;
    }

    @Override
    public String toString() {
        return String.format("color=%s, gearType=%s, numOfDoors=%d", this.color, this.gearType, this.numOfDoors);
    }
}
