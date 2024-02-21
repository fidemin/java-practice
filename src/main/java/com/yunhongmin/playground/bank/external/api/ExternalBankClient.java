package com.yunhongmin.playground.bank.external.api;

public abstract class ExternalBankClient {
    public abstract boolean send(String bankAccount, int amount);
}
