class Chapter2 {
    public static void main(String[] args) {
        long big = 100_000_000L;
        System.out.printf("big=%d\n", big);

        long hex = 0xFFFF_FFFF_FFFF_FFFFL;
        System.out.printf("hex=%x\n", hex);
        System.out.printf("hex=%#x\n", hex);
        System.out.printf("hex=%#X\n", hex);

        String name = "Java" + 8.0;
        String numToStr = "" + 7;
        System.out.println(name);
        System.out.println(numToStr);

        int age = 11;
        System.out.printf("age: %d%n", age);
        System.out.printf("age: %d\n", age);

        double d = 1.23456789;
        System.out.printf("d=%.10f\n", d);

        //Scanner scanner = new Scanner(System.in);
        //String input = scanner.nextLine();
        //System.out.printf("input: %s\n", input);

        Chapter2.CharToCode();

        float f2 = 12.1f;
        // declarative casting is required to convert float to int (from wider type to narrower type)
        int d2 = (int) f2;
    }

    static void CharToCode() {
        char ch1 = 'A';
        int code1 = (int) ch1;
        System.out.printf("%c=%d(%#X)\n", ch1, code1, code1);

        char ch2 = '가';
        int code2 = (int) ch2;
        System.out.printf("%c=%d(%#X)\n", ch2, code2, code2);


    }

}