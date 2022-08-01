class Chapter3 {
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

        System.out.println(Chapter3.UpperCase('a'));
        System.out.println(Chapter3.UpperCase('b'));
    }

    static char UpperCase(char lowerCase) {
        return (char)(lowerCase - 32);
    }
}