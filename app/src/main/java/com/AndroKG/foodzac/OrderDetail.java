package com.AndroKG.foodzac;

public class OrderDetail {
    String orderDate;
    String orderTime;
    String totalAmount;
    String orderItems;
    String orderPrice;
    String deliveryCharge;
    String promontionDeduction;
    String status;
    String restaurantName;

    public OrderDetail() {
    }

    public OrderDetail(String orderDate, String orderTime, String totalAmount, String orderItems, String orderPrice, String deliveryCharge, String promontionDeduction, String status, String restaurantName) {
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.totalAmount = totalAmount;
        this.orderItems = orderItems;
        this.orderPrice = orderPrice;
        this.deliveryCharge = deliveryCharge;
        this.promontionDeduction = promontionDeduction;
        this.status = status;
        this.restaurantName = restaurantName;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(String orderItems) {
        this.orderItems = orderItems;
    }

    public String getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(String orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getDeliveryCharge() {
        return deliveryCharge;
    }

    public void setDeliveryCharge(String deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getPromontionDeduction() {
        return promontionDeduction;
    }

    public void setPromontionDeduction(String promontionDeduction) {
        this.promontionDeduction = promontionDeduction;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
