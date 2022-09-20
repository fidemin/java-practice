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

        GarbageCollector gc = new GarbageCollector();
        gc.setDaemon(true);
        gc.start();

        int requiredMemory = 0;

        for (int i = 0; i < 20; i++) {
            requiredMemory = (int) (Math.random() * 10) * 20;
            if (gc.freeMemory() < requiredMemory || gc.freeMemory() < gc.totalMemory() * 0.4) {
                gc.interrupt();
                gc.join(100);
            }

            gc.setUsedMemory(gc.getUsedMemory() + requiredMemory);
            System.out.println("usedMemory: " + gc.getUsedMemory());
        }
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

class GarbageCollector extends Thread {
    private final int MAX_MEMORY = 1000;

    private int usedMemory = 0;
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(10 * 1000);
            } catch (InterruptedException e) {
                System.out.println("exception = " + e);
                gc();
                System.out.println("GC: free memory:" + freeMemory());
            }
        }
    }

    public void gc() {
        usedMemory -= 300;
        if (usedMemory < 0) usedMemory = 0;
    }

    public int totalMemory() {
        return MAX_MEMORY;
    }

    public int freeMemory() {
        return MAX_MEMORY - usedMemory;
    }

    public int getUsedMemory() {
        return usedMemory;
    }

    public void setUsedMemory(int usedMemory) {
        this.usedMemory = usedMemory;
    }
}
