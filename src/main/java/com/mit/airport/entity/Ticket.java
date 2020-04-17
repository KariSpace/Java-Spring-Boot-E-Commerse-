/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mit.airport.entity;

import java.io.Serializable;
import java.util.Date;
 
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
/**
 *
 * @author Asus
 */
 

 
@Entity
@Table(name = "Tickets")
public class Ticket implements Serializable {
 
    private static final long serialVersionUID = -1000119078147252957L;
 
    @Id
    @Column(name = "Code", length = 20, nullable = false)
    private String code;
 
    @Column(name = "Name", length = 255, nullable = false)
    private String name;
 
    @Column(name = "Price", nullable = false)
    private double price;
 
     
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "Create_Date", nullable = false)
    private Date createDate;
 
    public Ticket() {
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
 
    public Date getCreateDate() {
        return createDate;
    }
 
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
 
    
 
}
