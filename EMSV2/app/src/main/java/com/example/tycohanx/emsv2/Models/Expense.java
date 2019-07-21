package com.example.tycohanx.emsv2.Models;

public class Expense {
    private int Amount;
    private String Category;
    private long Date;
    private String Description;
    private int Id;
    private String SubCategory;
    private int balance;
    private int is_secondary;
    private int uid;

    public int getUid() {
        return this.uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public Expense(int id, int amount, String category, String subCategory, String description, long date, int Uid, int Balance, int Is_secondary) {
        this.Id = id;
        this.Amount = amount;
        this.Category = category;
        this.SubCategory = subCategory;
        this.Description = description;
        this.Date = date;
        Uid = this.uid;
        this.balance = Balance;
        this.is_secondary = Is_secondary;
    }

    public int getBalance() {
        return this.balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getIs_secondary() {
        return this.is_secondary;
    }

    public void setIs_secondary(int is_secondary) {
        this.is_secondary = is_secondary;
    }

    public Expense(int amount, String category, String subCategory, String description, long date, int Balance, int Is_secondary) {
        this.Amount = amount;
        this.Category = category;
        this.SubCategory = subCategory;
        this.Description = description;
        this.Date = date;
        this.is_secondary = Is_secondary;
        this.balance = Balance;
    }

    public int getId() {
        return this.Id;
    }

    public int getAmount() {
        return this.Amount;
    }

    public void setAmount(int amount) {
        this.Amount = amount;
    }

    public String getCategory() {
        return this.Category;
    }

    public void setCategory(String category) {
        this.Category = category;
    }

    public String getSubCategory() {
        return this.SubCategory;
    }

    public void setSubCategory(String subCategory) {
        this.SubCategory = subCategory;
    }

    public String getDescription() {
        return this.Description;
    }

    public void setDescription(String description) {
        this.Description = description;
    }

    public long getDate() {
        return this.Date;
    }

    public void setDate(long date) {
        this.Date = date;
    }
}
