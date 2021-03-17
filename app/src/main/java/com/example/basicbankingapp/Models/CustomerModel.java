package com.example.basicbankingapp.Models;

public class CustomerModel {

    private String customerName, customerEmail, customerBal;
    private int id;
    private String userFrom, userTo, transactionStatus, transactionId, transactionDate;

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CustomerModel(int id, String customerName, String customerEmail) {
        this.id = id;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
    }

    public CustomerModel(int id, String customerName, String customerEmail, String customerBal) {
        this.id = id;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerBal = customerBal;
    }



    public CustomerModel(String userFrom, String userTo, String customerBal,String transactionStatus, String transactionId, String transactionDate) {
        this.userFrom = userFrom;
        this.userTo = userTo;
        this.customerBal = customerBal;
        this.transactionStatus = transactionStatus;
        this.transactionId = transactionId;
        this.transactionDate = transactionDate;
    }

    public String getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(String userFrom) {
        this.userFrom = userFrom;
    }

    public String getUserTo() {
        return userTo;
    }

    public void setUserTo(String userTo) {
        this.userTo = userTo;
    }

    public String getTransactionTime() {
        return transactionStatus;
    }

    public void setTransactionTime(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerBal() {
        return customerBal;
    }

    public void setCustomerBal(String customerBal) {
        this.customerBal = customerBal;
    }
}
