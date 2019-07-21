package com.example.tycohanx.emsv2.Models;

public class Balance {
    private int Id;
    private int account;
    private int cash;

    public int getId() {
        return this.Id;
    }

    public Balance(int Id, int cash, int account) {
        this.Id = Id;
        this.cash = cash;
        this.account = account;
    }

    public Balance(int cash, int account) {
        this.cash = cash;
        this.account = account;
    }

    public int getCash() {
        return this.cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }

    public int getAccount() {
        return this.account;
    }

    public void setAccount(int account) {
        this.account = account;
    }
}
