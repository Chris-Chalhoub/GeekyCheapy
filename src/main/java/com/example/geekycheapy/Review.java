package com.example.geekycheapy;

public class Review {
    String reviewText;
    String userID;
    String productName;
    String userEmail;
    public Review(){

    }
    public Review(String reviewText, String userID, String productName, String userEmail) {
        this.reviewText = reviewText;
        this.userID = userID;
        this.productName = productName;
        this.userEmail = userEmail;
    }
    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
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

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }



}
