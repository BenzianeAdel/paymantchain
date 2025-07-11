/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Service.java to edit this template
 */
package com.paymentchain.customer.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.paymentchain.customer.entities.Customer;
import com.paymentchain.customer.entities.CustomerProduct;
import com.paymentchain.customer.exception.BussinesRuleException;
import com.paymentchain.customer.repository.CustomerRepository;
import io.netty.channel.ChannelOption;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import java.net.UnknownHostException;
import java.time.Duration;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.netty.http.client.HttpClient;

/**
 *
 * @author adelb
 */
@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    
    @Autowired
    private  WebClient.Builder webClientBuilder;

    private final HttpClient client = HttpClient.create()
        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
        .option(ChannelOption.SO_KEEPALIVE, true)
        .option(EpollChannelOption.TCP_KEEPIDLE, 300)
        .option(EpollChannelOption.TCP_KEEPINTVL, 60)
        .responseTimeout(Duration.ofSeconds(1))
        .doOnConnected(connection -> {
            connection.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS));
            connection.addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS));
        });

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    public List<Customer> getCustomerAll(){
        return customerRepository.findAll();
    }
    public Customer guardarCustomer(Customer entrada)throws BussinesRuleException,UnknownHostException {
        if(entrada.getProducts() != null){
            Iterator<CustomerProduct> products = entrada.getProducts().iterator();
            while(products.hasNext()){
                CustomerProduct p = products.next();
                if(getProductName(p.getProductId()).isBlank()){
                    throw new BussinesRuleException("1025", "Error validacion, producto con id "+p.getProductId()+ " no existe", HttpStatus.PRECONDITION_FAILED);
                }
                else{
                    p.setCustomer(entrada);
                }
               
            }
        }
        return customerRepository.save(entrada);
    }
    public Customer modificarCustomer(long id,Customer input){
        Customer newCustomer = getCustomer(id).get();
        newCustomer.setName(input.getName());
        newCustomer.setPhone(input.getPhone());
        newCustomer.setAddress(input.getAddress());
        newCustomer.setIban(input.getIban());
        newCustomer.setSurname(input.getSurname());
        newCustomer.setCode(input.getCode());
        Customer modificado = customerRepository.save(newCustomer);
        return modificado;
    }
    public Optional<Customer> getCustomer(long id){
        return customerRepository.findById(id);
    }
    public void eliminarCustomer(long id){
        customerRepository.deleteById(id);
    }
    
    private String getProductName(long id) throws UnknownHostException { 
        String name;
        try{
        WebClient build = webClientBuilder.clientConnector(new ReactorClientHttpConnector(client))
                .baseUrl("http://product/product")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultUriVariables(Collections.singletonMap("url", "http://product/product"))
                .build();
        JsonNode block = build.method(HttpMethod.GET).uri("/" + id)
                .retrieve().bodyToMono(JsonNode.class).block();
        name = block.get("name").asText();
        }catch(WebClientResponseException ex){
            if(ex.getStatusCode() == HttpStatus.NOT_FOUND){
                return "";
            }else{
                throw new UnknownHostException(ex.getMessage());
            }
        }
        return name;
    }
    private List<?> getTransactions(String iban) throws UnknownHostException {
        Optional<List<?>> transactionsOptional;
        try{
        WebClient build = webClientBuilder.clientConnector(new ReactorClientHttpConnector(client))
                .baseUrl("http://transaction/transaction")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();       
        
        transactionsOptional = Optional.ofNullable(build.method(HttpMethod.GET)
        .uri(uriBuilder -> uriBuilder
                .path("/transactionByIban")
                .queryParam("ibanAccount", iban)
                .build())
        .retrieve()
        .bodyToFlux(Object.class)
        .collectList()
        .block());       
        }catch(WebClientResponseException ex){
            if(ex.getStatusCode() == HttpStatus.NOT_FOUND){
                return Collections.emptyList();
            }else{
                throw new UnknownHostException(ex.getMessage());
            }
        }
        return transactionsOptional.orElse(Collections.emptyList());
    }
  
    public Customer getByCode(String code) throws UnknownHostException {
        Customer customer = customerRepository.findByCode(code);
        if(customer != null){
        List<CustomerProduct> products = customer.getProducts();
        products.forEach(x ->{
            try{
                String productName = getProductName(x.getProductId());
            x.setProductName(productName);
            } catch (UnknownHostException ex) {
                    Logger.getLogger(CustomerService.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        });
        }
        customer.setTransactions(getTransactions(customer.getIban()));
        return customer;
    }
}
