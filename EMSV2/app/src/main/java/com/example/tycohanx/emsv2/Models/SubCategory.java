package com.example.tycohanx.emsv2.Models;

public class SubCategory {
    private int CatId;
    private int Id;
    private String Name;

    public int getId() {
        return this.Id;
    }

    public int getCatId() {
        return this.CatId;
    }

    public void setCatId(int catId) {
        this.CatId = catId;
    }

    public String getName() {
        return this.Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public SubCategory(int catId, String name) {
        this.CatId = catId;
        this.Name = name;
    }

    public SubCategory(int id, int catId, String name) {
        this.Id = id;
        this.CatId = catId;
        this.Name = name;
    }
}
