package com.yunhongmin.javabasic;

import java.util.Vector;

public class Chapter07 {
    class InstanceInner {
        int iv = 100;
        // static int CONST = 100; // static for instance inner class is not permitted without final
        // final + static -> constant!!
        final static int CONST = 100;
    }

    static class StaticInner {
        int iv = 200;
        static int cv = 200;
    }

    public static void main(String[] args) {
        Deck deck = new Deck();
        System.out.println(deck.pick());
        System.out.println(deck.pick());

        System.out.println(deck.pick(0));
        deck.shuffle();
        System.out.println(deck.pick(0));

        Product p1 = new Tv();
        Product p2 = new Radio();
        Product p3 = new Radio();
        Product p4 = new Tv();
        Product p5 = new Tv();

        Buyer yunhong = new Buyer("Yunhong", 1500_000);
        yunhong.buy(p1);
        yunhong.buy(p2);
        yunhong.buy(p3);
        yunhong.buy(p4);
        yunhong.summary();
        yunhong.refund(p1);
        yunhong.refund(p2);
        yunhong.refund(p5);

        System.out.println(InstanceInner.CONST);
        // InstanceInner ii = new InstanceInner(); // In static method, only static class instance and member variable can be used.

        System.out.println(StaticInner.cv);
        StaticInner s = new StaticInner();
        System.out.println(s.iv);
    }
}

class Deck {
    final int CARD_NUM = Card.KIND_MAX * Card.NUM_MAX;
    final Card[] cards = new Card[CARD_NUM];

    Deck () {
        for (int k=0; k<Card.KIND_MAX; k++) {
            for (int n=0; n<Card.NUM_MAX; n++) {
                cards[k*Card.NUM_MAX+n] = new Card(k, n);
            }
        }
    }

    public Card pick() {
        int pos = (int) (Math.random() * this.cards.length);
        return cards[pos];
    }

    public Card pick(int pos) {
        return cards[pos];
    }

    public void shuffle() {
        for (int i=0; i<cards.length; i++) {
            int j = (int) (Math.random() * cards.length);
            Card tmp = cards[i];
            cards[i] = cards[j];
            cards[j] = tmp;
        }
    }
}


class Card {
    static final int KIND_MAX = 4;
    static final int NUM_MAX = 13;

    static final String[] kinds = {"SPADE", "DIAMOND", "HEART", "CLOVER"};
    static final String numbers = "123456789XJQK";

    final int kind;
    final int number;

    Card(int kind, int number) {
        this.kind = kind;
        this.number = number;
    }

    @Override
    public String toString() {
        return kinds[this.kind] + " " + numbers.charAt(this.number);
    }
}

interface Refundable {
    boolean canRefund();
}


abstract class Product implements Refundable {
    protected String name;
    protected int price;
    protected int points;

    Product(String name, int price, int points) {
        this.name = name;
        this.price = price;
        this.points = points;
    }

    public int getPrice() {
        return price;
    }

    public int getPoints() {
        return points;
    }

    @Override
    public String toString() {
        return this.name;
    }
}

class Tv extends Product {
    Tv() {
        super("TV", 1000_000, 1000);
    }

    @Override
    public boolean canRefund() {
        return true;
    }
}

class Radio extends Product {
    Radio() {
        super("Radio", 100_000, 1000);
    }

    @Override
    public boolean canRefund() {
        return false;
    }
}

class Buyer {
    private String name;
    private int money;
    private int points;

    private Vector<Product> products = new Vector<>();

    Buyer(String name, int money) {
        this.name = name;
        this.money = money;
        this.points = 0;
    }

    public void buy(Product p) {
        if (p.getPrice() > this.money) {
            System.out.printf("%s don't have enough money to buy %s.%n", this.name, p);
            return;
        }

        this.money -= p.getPrice();
        this.points += p.getPoints();
        this.products.add(p);
    }

    public void refund(Product p) {
        if (!p.canRefund()) {
            System.out.println("Can't refund");
            return;
        }

        if (products.remove(p)) {
            this.money += p.getPrice();
            this.points += p.getPoints();
        } else {
            System.out.println("You have no product to refund");
        }
    }

    public void summary() {
        System.out.printf("%s's summary\n", this.name);
        System.out.printf("money: %d, points: %d\n", this.money, this.points);
        System.out.printf("purchased products: ");
        for (int i=0; i<products.size(); i++) {
            if (i == 0) {
                System.out.printf("%s", products.get(i));
            } else {
                System.out.printf(",%s", products.get(i));
            }
        }
        System.out.println();
    }

    @Override
    public String toString() {
        return String.format("Buyer(name=%s, money=%d, points=%d", this.name, this.money, this.points);
    }
}