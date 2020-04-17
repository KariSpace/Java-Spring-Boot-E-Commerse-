/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mit.airport.validator;
import com.mit.airport.dao.TicketDAO;
import com.mit.airport.entity.Ticket;
import com.mit.airport.form.TicketForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
 
/**
 *
 * @author Asus
 */

@Component
public class ProductFormValidator implements Validator {
 
   @Autowired
   private TicketDAO productDAO;
 
   // This validator only checks for the TicketForm.
   @Override
   public boolean supports(Class<?> clazz) {
      return clazz == TicketForm.class;
   }
 
   @Override
   public void validate(Object target, Errors errors) {
      TicketForm productForm = (TicketForm) target;
 
      // Check the fields of TicketForm.
      ValidationUtils.rejectIfEmptyOrWhitespace(errors, "code", "NotEmpty.productForm.code");
      ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty.productForm.name");
      ValidationUtils.rejectIfEmptyOrWhitespace(errors, "price", "NotEmpty.productForm.price");
 
      String code = productForm.getCode();
      if (code != null && code.length() > 0) {
         if (code.matches("\\s+")) {
            errors.rejectValue("code", "Pattern.productForm.code");
         } else if (productForm.isNewTicket()) {
            Ticket product = productDAO.findTicket(code);
            if (product != null) {
               errors.rejectValue("code", "Duplicate.productForm.code");
            }
         }
      }
   }
 
}