/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.billing.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

/**
 *
 * @author adelb
 */
@Entity
@Data
public class Billing {
    @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
   private long id;
   private long customerId;
   private String number;
   private String detail;
   private double amount;  
}
