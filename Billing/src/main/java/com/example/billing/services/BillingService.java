/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Service.java to edit this template
 */
package com.example.billing.services;

import com.example.billing.entities.Billing;
import com.example.billing.repository.BillingRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author adelb
 */
@Service
public class BillingService {
    @Autowired
    private BillingRepository billingRepository;
    
    public List<Billing>getBillingAll(){
        return billingRepository.findAll();
    }
    public Optional<Billing>getBilling(long id){
        return billingRepository.findById(id);
    }
    public Billing modificarBilling(long id, Billing input){
        Billing encontrado = getBilling(id).get();
        encontrado.setAmount(input.getAmount());
        encontrado.setDetail(input.getDetail());
        encontrado.setNumber(input.getNumber());
        Billing setBilling = billingRepository.save(encontrado);
        return setBilling;
    }
    public void eliminarBilling(long id){
        billingRepository.deleteById(id);
    }
    public Billing guardarBilling(Billing entrada){
        return billingRepository.save(entrada);
    }
    
}
