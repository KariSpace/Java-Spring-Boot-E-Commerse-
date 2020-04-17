/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mit.airport.controller;

import java.io.IOException;
 
import javax.servlet.http.HttpServletRequest;

 
import com.mit.airport.dao.OrderDAO;
import com.mit.airport.dao.TicketDAO;
import com.mit.airport.entity.Ticket;
import com.mit.airport.form.CustomerForm;
import com.mit.airport.model.CartInfo;
import com.mit.airport.model.CustomerInfo;
import com.mit.airport.model.TicketInfo;
import com.mit.airport.pangination.PaginationResult;
import com.mit.airport.utils.Utils;
import com.mit.airport.validator.CustomerFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
 
/**
 *
 * @author Asus
 */


@Controller
@Transactional
public class MainController {
 
   @Autowired
   private OrderDAO orderDAO;
 
   @Autowired
   private TicketDAO ticketDAO;
 
   @Autowired
   private CustomerFormValidator customerFormValidator;
 
   @InitBinder
   public void myInitBinder(WebDataBinder dataBinder) {
      Object target = dataBinder.getTarget();
      if (target == null) {
         return;
      }
      System.out.println("Target=" + target);
 
      // Case update quantity in cart
      // (@ModelAttribute("cartForm") @Validated CartInfo cartForm)
      if (target.getClass() == CartInfo.class) {
 
      }
 
      // Case save customer information.
      // (@ModelAttribute @Validated CustomerInfo customerForm)
      else if (target.getClass() == CustomerForm.class) {
         dataBinder.setValidator(customerFormValidator);
      }
 
   }
 
   @RequestMapping("/403")
   public String accessDenied() {
      return "/403";
   }
 
   @RequestMapping("/")
   public String home() {
      return "index";
   }
 
   // Ticket List
   @RequestMapping({ "/ticketList" })
   public String listTicketHandler(Model model, //
         @RequestParam(value = "name", defaultValue = "") String likeName,
         @RequestParam(value = "page", defaultValue = "1") int page) {
      final int maxResult = 5;
      final int maxNavigationPage = 10;
 
      PaginationResult<TicketInfo> result = ticketDAO.queryTicket(page, //
            maxResult, maxNavigationPage, likeName);
 
      model.addAttribute("paginationTickets", result);
      return "tickettList";
   }
 
   @RequestMapping({ "/buyTicket" })
   public String listTicketHandler(HttpServletRequest request, Model model, //
         @RequestParam(value = "code", defaultValue = "") String code) {
 
      Ticket ticket = null;
      if (code != null && code.length() > 0) {
         ticket = ticketDAO.findTicket(code);
      }
      if (ticket != null) {
 
         //
         CartInfo cartInfo = Utils.getCartInSession(request);
 
         TicketInfo ticketInfo = new TicketInfo(ticket);
 
         cartInfo.addTicket(ticketInfo, 1);
      }
 
      return "redirect:/shoppingCart";
   }
 
   @RequestMapping({ "/shoppingCartRemoveTicket" })
   public String removeTicketHandler(HttpServletRequest request, Model model, //
         @RequestParam(value = "code", defaultValue = "") String code) {
      Ticket ticket = null;
      if (code != null && code.length() > 0) {
         ticket = ticketDAO.findTicket(code);
      }
      if (ticket != null) {
 
         CartInfo cartInfo = Utils.getCartInSession(request);
 
         TicketInfo ticketInfo = new TicketInfo(ticket);
 
         cartInfo.removeTicket(ticketInfo);
 
      }
 
      return "redirect:/shoppingCart";
   }
 
   // POST: Update quantity for ticket in cart
   @RequestMapping(value = { "/shoppingCart" }, method = RequestMethod.POST)
   public String shoppingCartUpdateQty(HttpServletRequest request, //
         Model model, //
         @ModelAttribute("cartForm") CartInfo cartForm) {
 
      CartInfo cartInfo = Utils.getCartInSession(request);
      cartInfo.updateQuantity(cartForm);
 
      return "redirect:/shoppingCart";
   }
 
   // GET: Show cart.
   @RequestMapping(value = { "/shoppingCart" }, method = RequestMethod.GET)
   public String shoppingCartHandler(HttpServletRequest request, Model model) {
      CartInfo myCart = Utils.getCartInSession(request);
 
      model.addAttribute("cartForm", myCart);
      return "shoppingCart";
   }
 
   // GET: Enter customer information.
   @RequestMapping(value = { "/shoppingCartCustomer" }, method = RequestMethod.GET)
   public String shoppingCartCustomerForm(HttpServletRequest request, Model model) {
 
      CartInfo cartInfo = Utils.getCartInSession(request);
 
      if (cartInfo.isEmpty()) {
 
         return "redirect:/shoppingCart";
      }
      CustomerInfo customerInfo = cartInfo.getCustomerInfo();
 
      CustomerForm customerForm = new CustomerForm(customerInfo);
 
      model.addAttribute("customerForm", customerForm);
 
      return "shoppingCartCustomer";
   }
 
   // POST: Save customer information.
   @RequestMapping(value = { "/shoppingCartCustomer" }, method = RequestMethod.POST)
   public String shoppingCartCustomerSave(HttpServletRequest request, //
         Model model, //
         @ModelAttribute("customerForm") @Validated CustomerForm customerForm, //
         BindingResult result, //
         final RedirectAttributes redirectAttributes) {
 
      if (result.hasErrors()) {
         customerForm.setValid(false);
         // Forward to reenter customer info.
         return "shoppingCartCustomer";
      }
 
      customerForm.setValid(true);
      CartInfo cartInfo = Utils.getCartInSession(request);
      CustomerInfo customerInfo = new CustomerInfo(customerForm);
      cartInfo.setCustomerInfo(customerInfo);
 
      return "redirect:/shoppingCartConfirmation";
   }
 
   // GET: Show information to confirm.
   @RequestMapping(value = { "/shoppingCartConfirmation" }, method = RequestMethod.GET)
   public String shoppingCartConfirmationReview(HttpServletRequest request, Model model) {
      CartInfo cartInfo = Utils.getCartInSession(request);
 
      if (cartInfo == null || cartInfo.isEmpty()) {
 
         return "redirect:/shoppingCart";
      } else if (!cartInfo.isValidCustomer()) {
 
         return "redirect:/shoppingCartCustomer";
      }
      model.addAttribute("myCart", cartInfo);
 
      return "shoppingCartConfirmation";
   }
 
   // POST: Submit Cart (Save)
   @RequestMapping(value = { "/shoppingCartConfirmation" }, method = RequestMethod.POST)
 
   public String shoppingCartConfirmationSave(HttpServletRequest request, Model model) {
      CartInfo cartInfo = Utils.getCartInSession(request);
 
      if (cartInfo.isEmpty()) {
 
         return "redirect:/shoppingCart";
      } else if (!cartInfo.isValidCustomer()) {
 
         return "redirect:/shoppingCartCustomer";
      }
      try {
         orderDAO.saveOrder(cartInfo);
      } catch (Exception e) {
 
         return "shoppingCartConfirmation";
      }
 
      // Remove Cart from Session.
      Utils.removeCartInSession(request);
 
      // Store last cart.
      Utils.storeLastOrderedCartInSession(request, cartInfo);
 
      return "redirect:/shoppingCartFinalize";
   }
 
   @RequestMapping(value = { "/shoppingCartFinalize" }, method = RequestMethod.GET)
   public String shoppingCartFinalize(HttpServletRequest request, Model model) {
 
      CartInfo lastOrderedCart = Utils.getLastOrderedCartInSession(request);
 
      if (lastOrderedCart == null) {
         return "redirect:/shoppingCart";
      }
      model.addAttribute("lastOrderedCart", lastOrderedCart);
      return "shoppingCartFinalize";
   }
 
  
     
   
 
}