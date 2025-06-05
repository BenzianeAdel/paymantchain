/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paymentchain.customer.entities;

import jakarta.persistence.*;
import lombok.Data;

/**
 *
 * @author adelb
 */
@Entity
@Data
public class Customer {
    
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private long id;
    private String name;
    private String phone;
}
