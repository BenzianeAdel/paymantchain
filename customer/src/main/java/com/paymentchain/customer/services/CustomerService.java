/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Service.java to edit this template
 */
package com.paymentchain.customer.services;

import com.paymentchain.customer.entities.Customer;
import com.paymentchain.customer.repository.CustomerRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author adelb
 */
@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
   
    public List<Customer> getCustomerAll(){
        return customerRepository.findAll();
    }
    public Customer guardarCustomer(Customer entrada){
        return customerRepository.save(entrada);
    }
    public Customer modificarCustomer(long id,Customer input){
        Customer newCustomer = getCustomer(id).get();
        newCustomer.setName(input.getName());
        newCustomer.setPhone(input.getPhone());
        Customer modificado = customerRepository.save(newCustomer);
        return modificado;
    }
    public Optional<Customer> getCustomer(long id){
        return customerRepository.findById(id);
    }
    public void eliminarCustomer(long id){
        customerRepository.deleteById(id);
    }
}
