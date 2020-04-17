/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mit.airport.utils;

import javax.servlet.http.HttpServletRequest;
import com.mit.airport.model.CartInfo;
/**
 *
 * @author AsusS
 */

 
public class Utils {
 
   // Tickets in the cart, stored in Session.
   public static CartInfo getCartInSession(HttpServletRequest request) {
 
      CartInfo cartInfo = (CartInfo) request.getSession().getAttribute("myCart");
 
    
      if (cartInfo == null) {
         cartInfo = new CartInfo();
          
         request.getSession().setAttribute("myCart", cartInfo);
      }
 
      return cartInfo;
   }
 
   public static void removeCartInSession(HttpServletRequest request) {
      request.getSession().removeAttribute("myCart");
   }
 
   public static void storeLastOrderedCartInSession(HttpServletRequest request, CartInfo cartInfo) {
      request.getSession().setAttribute("lastOrderedCart", cartInfo);
   }
 
   public static CartInfo getLastOrderedCartInSession(HttpServletRequest request) {
      return (CartInfo) request.getSession().getAttribute("lastOrderedCart");
   }
    
}