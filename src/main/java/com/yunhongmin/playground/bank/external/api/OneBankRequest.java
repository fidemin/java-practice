package com.yunhongmin.playground.bank.external.api;

public class OneBankRequest extends ExternalBankRequest {
    public String bankAccount;
    public int amount;

    public OneBankRequest(String bankAccount, int amount) {
        this.bankAccount = bankAccount;
        this.amount = amount;
    }
}
