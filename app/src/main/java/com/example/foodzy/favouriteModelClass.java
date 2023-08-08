package com.example.foodzy;

public class favouriteModelClass {
    private int foodimage;
    private String name;
    private String price;

    favouriteModelClass(int foodimage,String name,String price){
        this.foodimage = foodimage;
        this.name = name;
        this.price = price;
    }

    public int getFoodimage() {
        return foodimage;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }
}
