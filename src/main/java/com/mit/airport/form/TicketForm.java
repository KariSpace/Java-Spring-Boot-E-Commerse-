/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mit.airport.form;

import com.mit.airport.entity.Ticket;
import org.springframework.web.multipart.MultipartFile;
/**
 *
 * @author Asus
 */
 

 
public class TicketForm {
    private String code;
    private String name;
    private double price;
 
    private boolean newTicket = false;
 
    // Upload file.
    private MultipartFile fileData;
 
    public TicketForm() {
        this.newTicket= true;
    }
 
    public TicketForm(Ticket product) {
        this.code = product.getCode();
        this.name = product.getName();
        this.price = product.getPrice();
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
 
    public MultipartFile getFileData() {
        return fileData;
    }
 
    public void setFileData(MultipartFile fileData) {
        this.fileData = fileData;
    }
 
    public boolean isNewTicket() {
        return newTicket;
    }
 
    public void setNewTicket(boolean newTicket) {
        this.newTicket = newTicket;
    }
 
}