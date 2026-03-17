package com.yunhongmin.playground.multithreading;

import java.util.LinkedList;
import java.util.Queue;

public class WaitNotifyExample {

    static final int CAPACITY = 3;
    static final Object lock = new Object();
    static Queue<Integer> queue = new LinkedList<>();

    static class Producer implements Runnable {
        @Override
        public void run() {
            int item = 0;
            while (true) {
                synchronized (lock) {
                    while (queue.size() == CAPACITY) {
                        try {
                            System.out.println("[Producer] Queue full, waiting...");
                            lock.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                    queue.add(item);
                    System.out.println("[Producer] Produced: " + item++);
                    lock.notifyAll(); // wake up all waiting consumers
                }
                try { Thread.sleep(500); } catch (InterruptedException e) { return; }
            }
        }
    }

    static class Consumer implements Runnable {
        private final String name;

        Consumer(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            while (true) {
                synchronized (lock) {
                    while (queue.isEmpty()) {
                        try {
                            System.out.println("[" + name + "] Queue empty, waiting...");
                            lock.wait(); // releases lock and waits
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                    int item = queue.poll();
                    System.out.println("[" + name + "] Consumed: " + item);
                    lock.notify(); // wake up one waiting thread (e.g., producer)
                }
                try { Thread.sleep(800); } catch (InterruptedException e) { return; }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread producer = new Thread(new Producer(), "Producer");
        Thread consumer1 = new Thread(new Consumer("Consumer-1"), "Consumer-1");
        Thread consumer2 = new Thread(new Consumer("Consumer-2"), "Consumer-2");

        producer.start();
        consumer1.start();
        consumer2.start();

        Thread.sleep(5000);

        producer.interrupt();
        consumer1.interrupt();
        consumer2.interrupt();
    }
}
