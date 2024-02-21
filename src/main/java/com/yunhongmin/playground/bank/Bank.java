package com.yunhongmin.playground.bank;

import com.yunhongmin.playground.bank.external.ExternalBank;
import com.yunhongmin.playground.bank.external.ExternalBankName;

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

    public void transfer(Account fromAccount, ExternalBankName toBankName, String toBankAccount, int amount) {
        try {
            fromAccount.lock();

            ExternalBank externalBank = ExternalBank.fromExternalBankName(toBankName);
            boolean success = externalBank.transferTo(toBankAccount, amount);
            if (!success) {
                System.out.printf("Transfer failed: fromAccount %d, toBankName %s, toBankAccount %s, amount %d%n",
                        fromAccount.getId(), toBankName.name(), toBankAccount, amount);
                return;
            }

            fromAccount.addToBalance(-amount);
        } finally {
            fromAccount.unlock();
        }
    }
}
