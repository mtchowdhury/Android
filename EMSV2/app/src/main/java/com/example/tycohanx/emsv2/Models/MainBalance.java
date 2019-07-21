package com.example.tycohanx.emsv2.Models;

public class MainBalance {
    int Amount;
    long Date;
    String Description;
    int Id;
    int Is_Secondary;
    String Source;
    int Uid;

    public int getId() {
        return this.Id;
    }

    public String getSource() {
        return this.Source;
    }

    public void setSource(String source) {
        this.Source = source;
    }

    public String getDescription() {
        return this.Description;
    }

    public void setDescription(String description) {
        this.Description = description;
    }

    public int getAmount() {
        return this.Amount;
    }

    public void setAmount(int amount) {
        this.Amount = amount;
    }

    public long getDate() {
        return this.Date;
    }

    public void setDate(long date) {
        this.Date = date;
    }

    public int getUid() {
        return this.Uid;
    }

    public void setUid(int uid) {
        this.Uid = uid;
    }

    public int getIs_Secondary() {
        return this.Is_Secondary;
    }

    public void setIs_Secondary(int is_Secondary) {
        this.Is_Secondary = is_Secondary;
    }

    public MainBalance(String source, String description, int amount, long date, int uid, int is_Secondary) {
        this.Source = source;
        this.Description = description;
        this.Amount = amount;
        this.Date = date;
        this.Uid = uid;
        this.Is_Secondary = is_Secondary;
    }

    public MainBalance(int id, String source, String description, int amount, long date, int uid, int is_Secondary) {
        this.Id = id;
        this.Source = source;
        this.Description = description;
        this.Amount = amount;
        this.Date = date;
        this.Uid = uid;
        this.Is_Secondary = is_Secondary;
    }
}
