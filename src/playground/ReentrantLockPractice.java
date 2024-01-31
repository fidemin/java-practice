package playground;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockPractice {
    public static void main(String[] args) throws Exception {
        System.out.println("ReentrantLockPractice starts");
        AtomicBoolean active = new AtomicBoolean(true);

        MessageStore messageStore = new MessageStore();
        Runnable messageSender = new MessageSender(messageStore, active);
        Runnable messageReceiver1 = new MessageReceiver(messageStore, active);
        Runnable messageReceiver2 = new MessageReceiver(messageStore, active);

        Thread senderThread = new Thread(messageSender);
        Thread receiverThread1 = new Thread(messageReceiver1);
        Thread receiverThread2 = new Thread(messageReceiver2);

        receiverThread1.start();
        receiverThread2.start();

        System.out.println();

        Thread.sleep(1000);

        senderThread.start();

        receiverThread1.join();
        receiverThread2.join();
        senderThread.join();
    }
}

class MessageSender implements Runnable {
    private final MessageStore messageStore;
    private final AtomicBoolean active;

    Scanner scanner = new Scanner(System.in);

    public MessageSender(MessageStore messageStore, AtomicBoolean active) {
        this.messageStore = messageStore;
        this.active = active;
    }

    @Override
    public void run() {
        while (true) {
            String input = scanner.nextLine();
            if (input.equals("quit")) {
                active.set(false);
                break;
            }
            messageStore.sendMessage(input);
        }
    }
}

class MessageReceiver implements Runnable {
    private final MessageStore messageStore;
    private final AtomicBoolean active;

    public MessageReceiver(MessageStore messageStore, AtomicBoolean active) {
        this.messageStore = messageStore;
        this.active = active;
    }

    @Override
    public void run() {
        while (active.get()) {
            String message = messageStore.getMessage();
            if (message != null) {
                System.out.println(message);
            }
        }
    }
}

class MessageStore {
    private String message;
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    String getMessage() {
        lock.lock();
        try {
            boolean await = condition.await(1, TimeUnit.SECONDS);
            Thread thread = Thread.currentThread();
            if (await) {
                return this.message;
            }
            return null;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    void sendMessage(String message) {
        lock.lock();
        try {
            this.message = message;
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }
}


