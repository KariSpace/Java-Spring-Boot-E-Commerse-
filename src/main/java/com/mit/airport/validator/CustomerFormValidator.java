/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mit.airport.validator;
import org.apache.commons.validator.EmailValidator;
import com.mit.airport.form.CustomerForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
/**
 *
 * @author Asus
 */
 

 
@Component
public class CustomerFormValidator implements Validator {
 
   private EmailValidator emailValidator = EmailValidator.getInstance();
 
   // This validator only checks for the CustomerForm.
   @Override
   public boolean supports(Class<?> clazz) {
      return clazz == CustomerForm.class;
   }
 
   @Override
   public void validate(Object target, Errors errors) {
      CustomerForm custInfo = (CustomerForm) target;
 
      // Check the fields of CustomerForm.
      ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty.customerForm.name");
      ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty.customerForm.email");
      ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "NotEmpty.customerForm.address");
      ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phone", "NotEmpty.customerForm.phone");
 
      if (!emailValidator.isValid(custInfo.getEmail())) {
         errors.rejectValue("email", "Pattern.customerForm.email");
      }
   }
 
}