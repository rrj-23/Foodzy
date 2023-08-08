package com.example.foodzy;

import android.widget.ImageView;
import android.widget.TextView;

public class MenuOptionsModal {
    private int image;
    private String text;
    private double price;
    private int quantity = 0;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public MenuOptionsModal(int image, String text, double price) {
        this.image = image;
        this.text = text;
        this.price = price;
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
}
