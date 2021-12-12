package com.example.geekycheapy;

public class Order {
    String userID;
    String productName;
    public  Order(){

    }
    public Order( String userID, String productName) {
        this.userID = userID;
        this.productName = productName;
    }



    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
