/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mit.airport.controller;
import java.util.List;
 
import org.apache.commons.lang.exception.ExceptionUtils;
import com.mit.airport.dao.OrderDAO;
import com.mit.airport.dao.TicketDAO;
import com.mit.airport.entity.Ticket;
import com.mit.airport.form.TicketForm;
import com.mit.airport.model.OrderDetailInfo;
import com.mit.airport.model.OrderInfo;
import com.mit.airport.pangination.PaginationResult;
import com.mit.airport.validator.TicketFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
public class AdminController {
 
   @Autowired
   private OrderDAO orderDAO;
 
   @Autowired
   private TicketDAO ticketDAO;
 
   @Autowired
   private TicketFormValidator ticketFormValidator;
 
   @InitBinder
   public void myInitBinder(WebDataBinder dataBinder) {
      Object target = dataBinder.getTarget();
      if (target == null) {
         return;
      }
      System.out.println("Target=" + target);
 
      if (target.getClass() == TicketForm.class) {
         dataBinder.setValidator(ticketFormValidator);
      }
   }
 
   // GET: Show Login Page
   @RequestMapping(value = { "/admin/login" }, method = RequestMethod.GET)
   public String login(Model model) {
 
      return "login";
   }
 
   @RequestMapping(value = { "/admin/accountInfo" }, method = RequestMethod.GET)
   public String accountInfo(Model model) {
 
      UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      System.out.println(userDetails.getPassword());
      System.out.println(userDetails.getUsername());
      System.out.println(userDetails.isEnabled());
 
      model.addAttribute("userDetails", userDetails);
      return "accountInfo";
   }
 
   @RequestMapping(value = { "/admin/orderList" }, method = RequestMethod.GET)
   public String orderList(Model model, //
         @RequestParam(value = "page", defaultValue = "1") String pageStr) {
      int page = 1;
      try {
         page = Integer.parseInt(pageStr);
      } catch (Exception e) {
      }
      final int MAX_RESULT = 5;
      final int MAX_NAVIGATION_PAGE = 10;
 
      PaginationResult<OrderInfo> paginationResult //
            = orderDAO.listOrderInfo(page, MAX_RESULT, MAX_NAVIGATION_PAGE);
 
      model.addAttribute("paginationResult", paginationResult);
      return "orderList";
   }
 
   // GET: Show ticket.
   @RequestMapping(value = { "/admin/ticket" }, method = RequestMethod.GET)
   public String ticket(Model model, @RequestParam(value = "code", defaultValue = "") String code) {
      TicketForm ticketForm = null;
 
      if (code != null && code.length() > 0) {
         Ticket ticket = ticketDAO.findTicket(code);
         if (ticket != null) {
            ticketForm = new TicketForm(ticket);
         }
      }
      if (ticketForm == null) {
         ticketForm = new TicketForm();
         ticketForm.setNewTicket(true);
      }
      model.addAttribute("ticketForm", ticketForm);
      return "ticket";
   }
 
   // POST: Save ticket
   @RequestMapping(value = { "/admin/ticket" }, method = RequestMethod.POST)
   public String ticketSave(Model model, //
         @ModelAttribute("ticketForm") @Validated TicketForm ticketForm, //
         BindingResult result, //
         final RedirectAttributes redirectAttributes) {
 
      if (result.hasErrors()) {
         return "ticket";
      }
      try {
         ticketDAO.save(ticketForm);
      } catch (Exception e) {
         Throwable rootCause = ExceptionUtils.getRootCause(e);
         String message = rootCause.getMessage();
         model.addAttribute("errorMessage", message);
         // Show ticket form.
         return "ticket";
      }
 
      return "redirect:/ticketList";
   }
 
   @RequestMapping(value = { "/admin/order" }, method = RequestMethod.GET)
   public String orderView(Model model, @RequestParam("orderId") String orderId) {
      OrderInfo orderInfo = null;
      if (orderId != null) {
         orderInfo = this.orderDAO.getOrderInfo(orderId);
      }
      if (orderInfo == null) {
         return "redirect:/admin/orderList";
      }
      List<OrderDetailInfo> details = this.orderDAO.listOrderDetailInfos(orderId);
      orderInfo.setDetails(details);
 
      model.addAttribute("orderInfo", orderInfo);
 
      return "order";
   }
 
}