package com.example.foodzy;
public class StaticMenuOptionsModal {
    private int image;
    private String text;
    private double price;
    private String desc;
    private int quantity = 0;
    private int star;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public StaticMenuOptionsModal(int image, String text, double price, int star, String desc) {
        this.image = image;
        this.text = text;
        this.price = price;
        this.star=star;
        this.desc = desc;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getStar(){return star;}
}
