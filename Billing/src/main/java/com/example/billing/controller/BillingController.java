/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */
package com.example.billing.controller;

import com.example.billing.entities.Billing;
import com.example.billing.services.BillingService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

/**
 *
 * @author adelb
 */
@RestController
@RequestMapping("/billing")
public class BillingController {
    @Autowired
    private BillingService billingService;
    
    @GetMapping()
    public List<Billing> findAll() {
        return billingService.getBillingAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable long id) {
        Optional<Billing>billing = billingService.getBilling(id);
        
        if(billing.isPresent()){
            return new ResponseEntity<>(billing.get(),HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable long id, @RequestBody Billing input) {
        Optional<Billing> billing = billingService.getBilling(id);
        if(billing.isPresent()){
            Billing modificado = billingService.modificarBilling(id, input);
            return new ResponseEntity<>(modificado,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping
    public ResponseEntity<?> post(@RequestBody Billing input) {
        Billing billing = billingService.guardarBilling(input);
        return ResponseEntity.ok(billing);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        Optional<Billing> billing = billingService.getBilling(id);
        if(billing.isPresent()){
            billingService.eliminarBilling(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
}
