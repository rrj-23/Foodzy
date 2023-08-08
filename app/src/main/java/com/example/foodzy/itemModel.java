package com.example.foodzy;
public class itemModel {
    private String name;
    private String price;
//    private String cross;
    private String quantity;
    private String fprice;


    itemModel(String name, String price, String quantity, String fprice){
        this.name = name;
        this.price = price;
//        this.cross = cross;
        this.quantity = quantity;
        this.fprice = fprice;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

//    public String getCross() {
//        return cross;
//    }

    public String getQuantity() {
        return quantity;
    }

    public String getFprice() {
        return fprice;
    }
}
