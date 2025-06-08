/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */
package com.paymentchain.transaction.controller;

import com.paymentchain.transaction.entities.Transaction;
import com.paymentchain.transaction.services.TransactionService;
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
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author adelb
 */
@RestController
@RequestMapping("/transaction")
public class TransactionRestController {
    @Autowired
    private TransactionService transactionService;
    
    @GetMapping()
    public List<Transaction> findAll() {
        return transactionService.getTransactionAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable long id) {
        Optional<Transaction> transaction = transactionService.getTransaction(id);
        if(transaction.isPresent()){
            return new ResponseEntity<>(transaction.get(),HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/transactionByIban")
    public List<Transaction> get(@RequestParam(name = "ibanAccount") String ibanAccount) {
      return transactionService.findByIbanAccount(ibanAccount);      
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable long id, @RequestBody Transaction input) {
        Optional<Transaction> transaction = transactionService.getTransaction(id);
        if(transaction.isPresent()){
            Transaction modificado = transactionService.modificarTransaction(id, input);
            return new ResponseEntity<>(modificado,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping
    public ResponseEntity<?> post(@RequestBody Transaction input) {
        Transaction transaction = transactionService.guardarTransaction(input);
        return ResponseEntity.ok(transaction);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        Optional<Transaction> transaction = transactionService.getTransaction(id);
        if(transaction.isPresent()){
            transactionService.eliminarTransaction(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
