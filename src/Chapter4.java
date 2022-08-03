public class Chapter4 {
    public static void main(String[] args) {
        PrintSeason(5);
        PrintSeason(7);
        PrintSeason(10);
        PrintSeason(1);
        PrintSeason(13);

        int[] arr = {10, 20, 30, 40, 50};
        for (int i: arr) {
            System.out.println(i);
        }
    }

    public static void PrintSeason(int month) {
        switch (month) {
            case 3: case 4: case 5:
                System.out.println("Spring");
                break;
            case 6: case 7: case 8:
                System.out.println("Summer");
                break;
            case 9: case 10: case 11:
                System.out.println("Fall");
                break;
            case 12: case 1: case 2:
                System.out.println("Winter");
                break;
            default:
                System.out.println("Wrong month");
        }
    }
}
