/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */
package com.paymentchain.customer.controller;

import com.paymentchain.customer.entities.Customer;
import com.paymentchain.customer.services.CustomerService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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
@RequestMapping("/customer")
public class CustomerRestController {
    @Autowired
    private CustomerService customerService;
    
    @Autowired
    private Environment env;
    @GetMapping("/check")
    public String check(){
        return "Hello your property value is "+ env.getProperty("custom.activeprofileName");
    }
    
    @GetMapping()
    public ResponseEntity<?>findAll() {
        List<Customer> customers = customerService.getCustomerAll();
        if(customers.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        else{
            return ResponseEntity.ok(customers);
        }
    }
    @GetMapping("/full")
    public Customer getByCode(@RequestParam(name = "code") String code) {
        return customerService.getByCode(code);
       
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") long id) {
        Optional<Customer> customer = customerService.getCustomer(id);
        if(customer.isPresent()){
            return new ResponseEntity<>(customer.get(),HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable("id") long id, @RequestBody Customer input) {
        Optional<Customer> customer = customerService.getCustomer(id);
        if(customer.isPresent()){
            Customer modificado = customerService.modificarCustomer(id, input);
            return new ResponseEntity<>(modificado,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping
    public ResponseEntity<?> post(@RequestBody Customer input) {
        Customer customer = customerService.guardarCustomer(input);
        return ResponseEntity.status(HttpStatus.CREATED).body(customer);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        Optional<Customer> customer = customerService.getCustomer(id);
        if(customer.isPresent()){
            customerService.eliminarCustomer(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
