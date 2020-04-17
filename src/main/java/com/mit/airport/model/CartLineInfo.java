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
  
    private ProductInfo productInfo;
    private int quantity;
  
    public CartLineInfo() {
        this.quantity = 0;
    }
  
    public ProductInfo getProductInfo() {
        return productInfo;
    }
  
    public void setProductInfo(ProductInfo productInfo) {
        this.productInfo = productInfo;
    }
  
    public int getQuantity() {
        return quantity;
    }
  
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
  
    public double getAmount() {
        return this.productInfo.getPrice() * this.quantity;
    }
     
}