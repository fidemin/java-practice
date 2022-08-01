class Chapter3 {
    public static void main(String[] args) {
        int x, y;
        // 1. x = (y = 3) -> 2. x = 3
        x = y = 3;

        // automatic type change
        // byte + short -> int + int -> int
        // char + short -> int + int -> int
        byte b1 = 1;
        short s1 = 2;
        int i1 = b1 + s1;

        int i = 1;
        int j = 1;
        System.out.println(i++); // print 1
        System.out.println(++j); // print 2
    }
}