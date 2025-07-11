/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.billing.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 *
 * @author adelb
 */
@Data
public class BusinessRuleException extends Exception{
  
    private long id;
    private String code;   
    private HttpStatus httpStatus;
    
    public BusinessRuleException(long id, String code, String message,HttpStatus httpStatus) {
        super(message);
        this.id = id;
        this.code = code;
        this.httpStatus = httpStatus;
    }

    public BusinessRuleException(String code, String message,HttpStatus httpStatus) {
        super(message);
        this.code = code;
        this.httpStatus = httpStatus;
    }
    
    public BusinessRuleException(String message, Throwable cause) {
        super(message, cause);
    }
}
