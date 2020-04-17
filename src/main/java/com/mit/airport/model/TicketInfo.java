/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mit.airport.model;
import com.mit.airport.entity.Ticket;
/**
 *
 * @author Asus
 */

 
public class TicketInfo {
    private String code;
    private String name;
    private double price;
 
    public TicketInfo() {
    }
 
    public TicketInfo(Ticket ticket) {
        this.code = ticket.getCode();
        this.name = ticket.getName();
        this.price = ticket.getPrice();
    }
 
    // Using in JPA/Hibernate query
    public TicketInfo(String code, String name, double price) {
        this.code = code;
        this.name = name;
        this.price = price;
    }
 
    public String getCode() {
        return code;
    }
 
    public void setCode(String code) {
        this.code = code;
    }
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    public double getPrice() {
        return price;
    }
 
    public void setPrice(double price) {
        this.price = price;
    }
 
}