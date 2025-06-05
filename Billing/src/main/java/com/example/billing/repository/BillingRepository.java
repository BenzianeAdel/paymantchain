/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Repository.java to edit this template
 */
package com.example.billing.repository;

import com.example.billing.entities.Billing;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author adelb
 */
public interface BillingRepository extends JpaRepository<Billing, Long> {
    
}
