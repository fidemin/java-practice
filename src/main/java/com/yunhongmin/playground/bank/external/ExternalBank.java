package com.yunhongmin.playground.bank.external;

public abstract class ExternalBank {
    public static ExternalBank fromExternalBankName(ExternalBankName name) {
        if (name.equals(ExternalBankName.ONE_BANK)) {
            return new OneBank();
        }

        throw new RuntimeException(name.name() + " is not proper bank name");
    }
    public abstract boolean transferTo(String bankAccount, int amount);
}
