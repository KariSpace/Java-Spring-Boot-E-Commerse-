/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mit.airport.model;

/**
 *
 * @author Asus
 */

public class OrderDetailInfo {
    private String id;
 
    private String ticketCode;
    private String ticketName;
 
    private int quanity;
    private double price;
    private double amount;
 
    public OrderDetailInfo() {
 
    }
 
    // Using for JPA/Hibernate Query.
    public OrderDetailInfo(String id, String ticketCode, //
            String ticketName, int quanity, double price, double amount) {
        this.id = id;
        this.ticketCode = ticketCode;
        this.ticketName = ticketName;
        this.quanity = quanity;
        this.price = price;
        this.amount = amount;
    }
 
    public String getId() {
        return id;
    }
 
    public void setId(String id) {
        this.id = id;
    }
 
    public String getTicketCode() {
        return ticketCode;
    }
 
    public void setTicketCode(String ticketCode) {
        this.ticketCode = ticketCode;
    }
 
    public String getTicketName() {
        return ticketName;
    }
 
    public void setTicketName(String ticketName) {
        this.ticketName = ticketName;
    }
 
    public int getQuanity() {
        return quanity;
    }
 
    public void setQuanity(int quanity) {
        this.quanity = quanity;
    }
 
    public double getPrice() {
        return price;
    }
 
    public void setPrice(double price) {
        this.price = price;
    }
 
    public double getAmount() {
        return amount;
    }
 
    public void setAmount(double amount) {
        this.amount = amount;
    }
}