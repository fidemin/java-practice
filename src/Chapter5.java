import java.util.Arrays;

public class Chapter5 {
    public static void main(String[] args) {
        // three ways to initialize array
        int[] arr1 = new int[5];
        for (int i=0; i<arr1.length; i++) {
            arr1[i] = i + 1;
        }

        int[] arr2 = new int[]{1, 2, 3, 4, 5};

        int[] arr3 = {1, 2, 3, 4, 5};

        System.out.println(Arrays.toString(arr1));
        System.out.println(Arrays.toString(arr2));
        System.out.println(Arrays.toString(arr3));

    }
}
