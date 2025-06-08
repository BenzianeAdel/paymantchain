/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paymentchain.transaction.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Data;

/**
 *
 * @author adelb
 */
@Entity
@Data
public class Transaction {
    
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private long id;
    
    private String reference;
    private String accountIban;
    
    private LocalDateTime date;
    
    private double amount;
    
    private double fee;
    
    private String description;
    
    private String status;
    
    private String channel;
}
