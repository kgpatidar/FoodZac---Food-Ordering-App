package com.AndroKG.foodzac;

public class Price {
    String price;
    String nameOfItem;

    public Price() {
    }

    public Price(String price, String nameOfItem) {
        this.price = price;
        this.nameOfItem = nameOfItem;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNameOfItem() {
        return nameOfItem;
    }

    public void setNameOfItem(String nameOfItem) {
        this.nameOfItem = nameOfItem;
    }
}
