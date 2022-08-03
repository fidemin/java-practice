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

        // copy array
        int[] newArr = new int[arr1.length*2];
        System.arraycopy(arr1, 0, newArr, 0, arr1.length);
        System.out.println(Arrays.toString(newArr));

        // Lotto!
        Chapter5.lotto();
    }

    static void lotto() {
        int[] nums = new int[45];
        for (int i=0; i<nums.length; i++) {
            nums[i] = i + 1;
        }

        for (int i=0; i<6; i++) {
            int j = (int)(Math.random() * nums.length); // 0 ~ 44
            int tmp = nums[i];
            nums[i] = nums[j];
            nums[j] = tmp;
        }

        for (int i=0; i<6; i++) {
            System.out.print(nums[i] + " ");
        }
        System.out.println();
    }
}
