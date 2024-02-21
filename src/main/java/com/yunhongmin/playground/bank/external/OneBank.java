package com.yunhongmin.playground.bank.external;

public class OneBank extends ExternalBank {
    ExternalBankName bankName;
    public OneBank() {
        bankName = ExternalBankName.ONE_BANK;
    }

    @Override
    public boolean transferTo(String bankAccount, int amount) {
        System.out.printf("Transferred to %s %s with amount %d won%n", bankName, bankAccount, amount);
        return true;
    }
}
