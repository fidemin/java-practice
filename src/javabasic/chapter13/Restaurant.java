package javabasic.chapter13;

import java.util.ArrayList;

public class Restaurant {
    public static void main(String[] args) throws Exception {
        Table table = new Table();

        new Thread(new Cook(table), "Shef").start();
        new Thread(new Customer(table, "donut"), "donut customer").start();
        new Thread(new Customer(table, "burger"), "burger customer").start();

        Thread.sleep(5000);
        System.exit(0);
    }
}

class Table {
    private String[] dishNames = {"donut", "donut", "burger"};
    final int MAX_FOOD = 6;

    private ArrayList<String> dishes = new ArrayList<>();
    
    public synchronized void add(String dish) {
        while (dishes.size() >= MAX_FOOD) {
            String name = Thread.currentThread().getName();
            System.out.println(name + " is waiting");
            try {
                wait();
                Thread.sleep(500);
            } catch (InterruptedException e) {}
        }
        dishes.add(dish);
        notifyAll();
        System.out.println("Dishes: " + dishes.toString());
    }

    public void remove(String dishName) {
        synchronized (this) {
            String name = Thread.currentThread().getName();
            while (dishes.size() == 0) {
                System.out.println(name + " is waiting");
                try {
                    wait();
                    Thread.sleep(300);
                } catch (InterruptedException e) {}
            }
            while (true) {
                for (int i = 0; i< dishes.size(); i++) {
                    if (dishName.equals(dishes.get(i))) {
                        dishes.remove(i);
                        notifyAll();
                        return;
                    }
                }

                try {
                    System.out.println(name + " is waiting");
                    wait();
                    Thread.sleep(300);
                } catch (InterruptedException e) {}
            }

        }
    }

    public String chooseDish() {
        int idx = (int) (Math.random() * dishNames.length);
        System.out.println("idx = " + idx);
        return dishNames[idx];
    }
}

class Customer implements Runnable {
    private Table table;
    private String food;

    public Customer(Table table, String food) {
        this.table = table;
        this.food = food;
    }

    private void eatFood() {
        table.remove(food);
    }

    @Override
    public void run() {
        while (true) {
            try {Thread.sleep(200);} catch (InterruptedException e) {}
            String name = Thread.currentThread().getName();
            eatFood();
            System.out.println(name + " eats a " + food);
        }
    }
}

class Cook implements Runnable {
    public Cook(Table table) {
        this.table = table;
    }

    private Table table;

    private void cook() {
        table.add(table.chooseDish());
    }

    @Override
    public void run() {
        while (true) {
            cook();
            try {Thread.sleep(80);} catch (InterruptedException e) {}
        }
    }
}
