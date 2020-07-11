package com.AndroKG.foodzac;

public class Restaurant {
    private String name, location, type, vegNonVeg, rating, opentime, closetime, sellOfProduct;

    public Restaurant() {}

    public Restaurant(String name, String location, String type, String vegNonVeg, String rating, String opentime, String closetime, String sellOfProduct) {
        this.name = name;
        this.location = location;
        this.type = type;
        this.vegNonVeg = vegNonVeg;
        this.rating = rating;
        this.opentime = opentime;
        this.closetime = closetime;
        this.sellOfProduct = sellOfProduct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVegNonVeg() {
        return vegNonVeg;
    }

    public void setVegNonVeg(String vegNonVeg) {
        this.vegNonVeg = vegNonVeg;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getOpentime() {
        return opentime;
    }

    public void setOpentime(String opentime) {
        this.opentime = opentime;
    }

    public String getClosetime() {
        return closetime;
    }

    public void setClosetime(String closetime) {
        this.closetime = closetime;
    }

    public String getSellOfProduct() {
        return sellOfProduct;
    }

    public void setSellOfProduct(String sellOfProduct) {
        this.sellOfProduct = sellOfProduct;
    }
}
