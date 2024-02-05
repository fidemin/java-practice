package playground;

public class GCHighPractice {
    public static void main(String[] args) {
        String str = "";

        for (int i = 0; i < 1000000; i++) {
            // This line would be very slow because of GC
            str += Integer.toString(i) + " ";
        }

        str += "end";
    }
}
