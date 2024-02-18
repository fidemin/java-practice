package com.yunhongmin.playground.bank;

public class Bank {
    public void transfer(Account fromAccount, Account toAccount, int amount) {
        try {
            // to prevent deadlock, the sequence of locking is sorted by id.
            if (fromAccount.getId() < toAccount.getId()) {
                fromAccount.lock();
                toAccount.lock();
            } else {
                toAccount.lock();
                fromAccount.lock();
            }
            fromAccount.addToBalance(-amount);
            toAccount.addToBalance(amount);
        } finally {
            try {
                fromAccount.unlock();
            } catch (Exception ignored) {
            }

            try {
                toAccount.unlock();
            } catch (Exception ignored) {
            }
        }

    }
}
