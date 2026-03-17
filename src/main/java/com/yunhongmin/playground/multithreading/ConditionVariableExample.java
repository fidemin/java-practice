package com.yunhongmin.playground.multithreading;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionVariableExample {

    static final int CAPACITY = 3;
    static final ReentrantLock lock = new ReentrantLock();
    static final Condition notFull  = lock.newCondition(); // signaled when queue has space
    static final Condition notEmpty = lock.newCondition(); // signaled when queue has items
    static Queue<Integer> queue = new LinkedList<>();

    static class Producer implements Runnable {
        @Override
        public void run() {
            int item = 0;
            while (true) {
                lock.lock();
                try {
                    while (queue.size() == CAPACITY) {
                        System.out.println("[Producer] Queue full, waiting...");
                        notFull.await(); // wait until a consumer signals notFull
                    }
                    queue.add(item);
                    System.out.println("[Producer] Produced: " + item++);
                    notEmpty.signalAll(); // tell consumers there is an item
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                } finally {
                    lock.unlock();
                }
                try { Thread.sleep(500); } catch (InterruptedException e) { return; }
            }
        }
    }

    static class Consumer implements Runnable {
        private final String name;

        Consumer(String name) { this.name = name; }

        @Override
        public void run() {
            while (true) {
                lock.lock();
                try {
                    while (queue.isEmpty()) {
                        System.out.println("[" + name + "] Queue empty, waiting...");
                        notEmpty.await(); // wait until producer signals notEmpty
                    }
                    int item = queue.poll();
                    System.out.println("[" + name + "] Consumed: " + item);
                    notFull.signal(); // tell producer there is space
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                } finally {
                    lock.unlock();
                }
                try { Thread.sleep(800); } catch (InterruptedException e) { return; }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread producer  = new Thread(new Producer());
        Thread consumer1 = new Thread(new Consumer("Consumer-1"));
        Thread consumer2 = new Thread(new Consumer("Consumer-2"));

        producer.start();
        consumer1.start();
        consumer2.start();

        Thread.sleep(5000);

        producer.interrupt();
        consumer1.interrupt();
        consumer2.interrupt();
    }
}