package com.yunhongmin.playground.bank.external.bank;

import com.yunhongmin.playground.bank.external.api.ExternalBankClient;
import com.yunhongmin.playground.bank.external.api.OneBankClient;

public class OneBank extends ExternalBank {
    ExternalBankName bankName;
    ExternalBankClient client;
    public OneBank() {
        bankName = ExternalBankName.ONE_BANK;
        client = new OneBankClient();
    }

    @Override
    public boolean transferTo(String bankAccount, int amount) {
        client.send(bankAccount, amount);
        System.out.printf("Transferred to %s %s with amount %d won%n", bankName, bankAccount, amount);
        return true;
    }
}
