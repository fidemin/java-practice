package javabasic;

import javax.swing.*;

public class Chapter13 {
    public static void main(String[] args) throws Exception {
        Thread t1 = new Thread1();
        t1.start();

        Thread t2 = new Thread(() -> {
            for (int i=0;i<10;i++) {
                System.out.println("This is daemon");
                delay(1000);
            }
        });
        t2.setDaemon(true);
        t2.start();

        Thread t3 = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println("t3");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("InterruptedException happens: " + Thread.currentThread().isInterrupted());
                }
            }
        });

        t3.start();

        for (int i=0;i<10;i++) {
            System.out.println("i = " + i);
            Thread.sleep(300);
        }

        t3.interrupt();
    }

    public static void delay(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {}
    }
}

class Thread1 extends Thread {
    @Override
    public void run() {
        System.out.println(getThreadGroup().getName());
        throw new RuntimeException("Thread exception");
    }
}
