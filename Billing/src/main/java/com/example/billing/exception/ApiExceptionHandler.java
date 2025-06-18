/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.billing.exception;

import com.example.billing.common.StandarizedApiExceptionResponse;
import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 *
 * @author adelb
 */
@RestControllerAdvice//Indicate that this class assit a controller class and can have a body in response
public class ApiExceptionHandler {   
    
    //Allow define a method for handler this particular exception in transversal way, as a global exception handler
    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<StandarizedApiExceptionResponse> handleBusinessRuleExceptions(BusinessRuleException ex) {
        StandarizedApiExceptionResponse response = new StandarizedApiExceptionResponse(ex.getCode(),"", ex.getMessage());      
       return new ResponseEntity<>(response,  ex.getHttpStatus() != null ? ex.getHttpStatus() : HttpStatus.INTERNAL_SERVER_ERROR);
     
    }  
    
    @ExceptionHandler(IOException.class)
    public ResponseEntity<StandarizedApiExceptionResponse> handleNoContentException(IOException ex) {
        StandarizedApiExceptionResponse response = new StandarizedApiExceptionResponse("Input Ouput Error","erorr-1024",ex.getMessage());
        return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
}
