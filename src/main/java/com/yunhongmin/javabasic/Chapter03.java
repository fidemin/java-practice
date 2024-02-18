package com.yunhongmin.javabasic;

class Chapter03 {
    public static void main(String[] args) {
        int x, y;
        // 1. x = (y = 3) -> 2. x = 3
        x = y = 3;

        // automatic type change
        // byte + short -> int + int -> int
        // byte + byte -> int + int -> int
        // char + short -> int + int -> int
        // arithmetic operation of any byte, short, char will be converted to int automatically!
        byte b1 = 1;
        short s1 = 2;
        int i1 = b1 + s1;

        int i = 1;
        int j = 1;
        System.out.println(i++); // print 1
        System.out.println(++j); // print 2

        System.out.println(10/4); // print 2
        System.out.println(10.0f/4); // print 2.5
        System.out.println(10.0f/4.0f); // print 2.5

        byte a = 10;
        byte b = 20;
        // c is not 200. data loss happens.
        byte c = (byte)(a * b); // casting required
        System.out.println(c);

        int i2 = 1_000_000;
        int i3 = 2_000_000;
        long l1 = i2 * i3; // l1 = 2_000_000_000_000 ? No!
        System.out.println(l1);
        l1 = (long) i2 * i3; // l1 = 2_000_000_000_000 ? Yes!
        System.out.println(l1);

        System.out.println(Chapter03.upperCase('a'));
        System.out.println(Chapter03.upperCase('b'));

        // integer 10 will be transformed to 10.0f before operation
        System.out.printf("100 == 10.0f \t %b\n", 10==10.0f);

        // use equals method for string comparison.
        String str1 = "abc";
        String str2 = new String("abc");
        System.out.println(str1 == str2); // false
        System.out.println(str1.equals(str2)); // true

    }

    static char upperCase(char lowerCase) {
        return (char)(lowerCase - 32);
    }
}