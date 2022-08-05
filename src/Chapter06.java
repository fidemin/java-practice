public class Chapter06 {
    public static void main(String[] args) {
        System.out.println(add(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));

        Car car = new Car("blue", "auto", 4);
        System.out.println(car);
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
    String color;
    String gearType;
    int numOfDoors;

    Car(String color, String gearType, int numOfdoors) {
        this.color = color;
        this.gearType = gearType;
        this.numOfDoors = numOfdoors;
    }

    @Override
    public String toString() {
        return String.format("color=%s, gearType=%s, numOfDoors=%d", this.color, this.gearType, this.numOfDoors);
    }
}
