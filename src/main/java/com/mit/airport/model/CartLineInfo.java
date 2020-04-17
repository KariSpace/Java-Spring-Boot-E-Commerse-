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

public class CartLineInfo {
  
    private TicketInfo ticketInfo;
    private int quantity;
  
    public CartLineInfo() {
        this.quantity = 0;
    }
  
    public TicketInfo getTicketInfo() {
        return ticketInfo;
    }
  
    public void setTicketInfo(TicketInfo ticketInfo) {
        this.ticketInfo = ticketInfo;
    }
  
    public int getQuantity() {
        return quantity;
    }
  
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
  
    public double getAmount() {
        return this.ticketInfo.getPrice() * this.quantity;
    }
     
}