package com.yunhongmin.playground.bank.external.api;

public class OneBankClient extends ExternalBankClient {
    public boolean send(String bankAccount, int amount) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            return false;
        }
        return true;
    }

    @Override
    public ExternalBankResponse send(ExternalBankRequest request) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            return new OneBankResponse(500);
        }
        return new OneBankResponse(200);
    }
}
