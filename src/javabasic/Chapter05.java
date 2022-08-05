package javabasic;

import java.util.Arrays;

public class Chapter05 {
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
        lotto();

        // 2d array
        int[][] scores = {
                {100, 100, 100},
                {50, 60, 80},
                {10, 20, 30}
        };

        int sum = 0;
        for (int[] row: scores) {
            for (int score: row) {
                System.out.printf("%d ", score);
                sum += score;
            }
        }
        System.out.println();
        System.out.printf("Total scores: %d\n", sum);

        int[][] m1 = {
                {1, 2, 3},
                {4, 5, 6}
        };

        int[][] m2 = {
                {1, 2},
                {3, 4},
                {5, 6}
        };

        int[][] m3 = Chapter05.multiplyMatrix(m1, m2);
        for (int[] row: m3) {
            for (int value: row) {
                System.out.printf("%3d", value);
            }
            System.out.println();
        }
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

    static int[][] multiplyMatrix(int[][] A, int[][] B) {
        int[][] result = new int[A.length][B[0].length];

        for (int i=0; i<result.length; i++) {
            for (int j=0; j<result[i].length; j++) {
                for (int k=0; k<A[i].length; k++) {
                    result[i][j] += A[i][k] * B[k][j];
                }
            }
        }
        return result;
    }
}
