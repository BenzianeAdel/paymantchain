/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Service.java to edit this template
 */
package com.paymentchain.transaction.services;

import com.paymentchain.transaction.entities.Transaction;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.paymentchain.transaction.repository.TransactionRepository;

/**
 *
 * @author adelb
 */
@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
   
    public List<Transaction> getTransactionAll(){
        return transactionRepository.findAll();
    }
    public Transaction guardarTransaction(Transaction entrada){
        return transactionRepository.save(entrada);
    }
    public Transaction modificarTransaction(long id,Transaction input){
        Transaction newTransaction = getTransaction(id).get();
        newTransaction.setAccountIban(input.getAccountIban());
        newTransaction.setAmount(input.getAmount());
        newTransaction.setDescription(input.getDescription());
        newTransaction.setChannel(input.getChannel());
        newTransaction.setFee(input.getFee());
        newTransaction.setDate(input.getDate());
        newTransaction.setReference(input.getReference());
        newTransaction.setStatus(input.getStatus());
        Transaction modificado = transactionRepository.save(newTransaction);
        return modificado;
    }
    public Optional<Transaction> getTransaction(long id){
        return transactionRepository.findById(id);
    }
    public List<Transaction>findByIbanAccount(String iban){
        return transactionRepository.findByIbanAccount(iban);
    }
    public void eliminarTransaction(long id){
        transactionRepository.deleteById(id);
    }
}
