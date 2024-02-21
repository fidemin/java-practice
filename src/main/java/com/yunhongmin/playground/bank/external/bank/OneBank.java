package com.yunhongmin.playground.bank.external.bank;

import com.yunhongmin.playground.bank.external.api.*;

public class OneBank extends ExternalBank {
    ExternalBankName bankName;
    ExternalBankClient client;
    public OneBank() {
        bankName = ExternalBankName.ONE_BANK;
        client = new OneBankClient();
    }

    @Override
    public boolean transferTo(String bankAccount, int amount) {
        ExternalBankRequest request = new OneBankRequest(bankAccount, amount);
        ExternalBankResponse response = client.send(request);
        if (response.resultCode != 200) {
            System.out.printf("Transfer failed: %s %s with amount %d won%n", bankName, bankAccount, amount);
            return false;
        }

        System.out.printf("Transferred to %s %s with amount %d won%n", bankName, bankAccount, amount);
        return true;
    }
}
