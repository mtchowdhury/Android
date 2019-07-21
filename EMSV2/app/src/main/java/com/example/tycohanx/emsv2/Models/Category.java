package com.example.tycohanx.emsv2.Models;

public class Category {
    private int Id;
    private String name;

    public int getId() {
        return this.Id;
    }

    public Category(String name) {
        this.name = name;
    }

    public Category(int id, String name) {
        this.Id = id;
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
