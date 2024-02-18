package com.yunhongmin.playground.bank;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

public class BankSimulator {
    public static void main(String[] args) throws InterruptedException {
        Bank bank = new Bank();

        Account account1 = new Account(1, "Tom");
        Account account2 = new Account(2, "Jerry");

        TransferSimulator simulator1 = new TransferSimulator(bank, account1, account2, 1);
        TransferSimulator simulator2 = new TransferSimulator(bank, account2, account1, 1);

        Thread thread1 = new Thread(simulator1);
        Thread thread2 = new Thread(simulator2);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("Account1 balance: " + account1.getBalance());
        System.out.println("Account2 balance: " + account2.getBalance());
    }

}

@Getter
@Setter
@RequiredArgsConstructor
class TransferSimulator implements Runnable {
    final private Bank bank;
    final private Account fromAccount;
    final private Account toAccount;
    final private int oneAmount;

    @Override
    public void run() {
        for (int i=0; i<1000; i++) {
            bank.transfer(fromAccount, toAccount, oneAmount);
        }
    }
}
