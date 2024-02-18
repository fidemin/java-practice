package com.yunhongmin.javabasic;

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
            System.out.println(e.getMessage());
        }

        IntArithmetic a1 = new IntArithmetic();
        try {
            a1.divide(2, 0);
        } catch (IntArithmeticException e) {
            System.out.println(e.getMessage());
        }

        throw new RuntimeException(); // RuntimeException is not required to be dealt: unchecked exception
    }
}

class IntArithmeticException extends Exception {
    // Exception is checked exception. User is required to deal with exception.
    // But in these days, RuntimeException which is unchecked exception is preferable, because user can choose to deal with exception.
    public IntArithmeticException() {
       super();
    }
    public IntArithmeticException(String message) {
       super(message);
    }
}

class IntArithmetic {
    int divide(int a, int b) throws IntArithmeticException {

        int result;
        try {
            result = a / b;
        } catch (ArithmeticException e) {
            throw new IntArithmeticException("divided by zero");
        }
        return result;
    }
}
