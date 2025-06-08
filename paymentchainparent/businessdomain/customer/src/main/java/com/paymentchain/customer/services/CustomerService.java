/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Service.java to edit this template
 */
package com.paymentchain.customer.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.paymentchain.customer.entities.Customer;
import com.paymentchain.customer.entities.CustomerProduct;
import com.paymentchain.customer.repository.CustomerRepository;
import io.netty.channel.ChannelOption;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

/**
 *
 * @author adelb
 */
@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final WebClient.Builder webClientBuilder;

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

    public CustomerService(CustomerRepository customerRepository, WebClient.Builder webClientBuilder) {
        this.customerRepository = customerRepository;
        this.webClientBuilder = webClientBuilder;
    }
    public List<Customer> getCustomerAll(){
        return customerRepository.findAll();
    }
    public Customer guardarCustomer(Customer entrada){
        entrada.getProducts().forEach(x -> x.setCustomer(entrada));
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
    
    private String getProductName(long id) { 
        WebClient build = webClientBuilder.clientConnector(new ReactorClientHttpConnector(client))
                .baseUrl("http://localhost:8082/product")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultUriVariables(Collections.singletonMap("url", "http://localhost:8082/product"))
                .build();
        JsonNode block = build.method(HttpMethod.GET).uri("/" + id)
                .retrieve().bodyToMono(JsonNode.class).block();
        String name = block.get("name").asText();
        return name;
    }
    private List<?> getTransactions(String iban) {
        WebClient build = webClientBuilder.clientConnector(new ReactorClientHttpConnector(client))
                .baseUrl("http://localhost:8083/transaction")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();       
        
        Optional<List<?>> transactionsOptional = Optional.ofNullable(build.method(HttpMethod.GET)
        .uri(uriBuilder -> uriBuilder
                .path("/transactionByIban")
                .queryParam("ibanAccount", iban)
                .build())
        .retrieve()
        .bodyToFlux(Object.class)
        .collectList()
        .block());       

        return transactionsOptional.orElse(Collections.emptyList());
    }
  
    public Customer getByCode(String code) {
        Customer customer = customerRepository.findByCode(code);
        List<CustomerProduct> products = customer.getProducts();
        products.forEach(x ->{
            String productName = getProductName(x.getId());
            x.setProductName(productName);
        });
        customer.setTransactions(getTransactions(customer.getIban()));
        return customer;
    }
}
