package com.yunhongmin.playground.bank;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


@Getter
@Setter
@RequiredArgsConstructor
public class Account {
    final private int id;
    final private String name;
    int balance = 0;
    Lock lock = new ReentrantLock();

    public void addToBalance(int amount) {
//        if (balance + amount < 0) {
//            throw new RuntimeException("balance is not enough");
//        }
        balance += amount;
    }

    public void lock() {
        lock.lock();
    }

    public void unlock() {
        lock.unlock();
    }
}
