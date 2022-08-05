public class Chapter06 {
    public static void main(String[] args) {
        System.out.println(add(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
    }

    static int add(int... nums) {
        int result = 0;
        for (int num: nums) {
            result += num;
        }
        return result;
    }
}
