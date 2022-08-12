package javabasic;

public class Chapter08 {
    public static void main(String[] args) {
        try {
            int i1 = 2/0;
        } catch (ArithmeticException | NullPointerException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        try {
            Exception e = new Exception("intended exception");
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        }

        throw new RuntimeException(); // RuntimeException is not required to be dealt: unchecked exception
    }


}
