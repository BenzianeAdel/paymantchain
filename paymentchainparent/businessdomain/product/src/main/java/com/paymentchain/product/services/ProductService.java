/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Service.java to edit this template
 */
package com.paymentchain.product.services;

import com.paymentchain.product.entities.Product;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.paymentchain.product.repository.ProductRepository;

/**
 *
 * @author adelb
 */
@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
   
    public List<Product> getProductAll(){
        return productRepository.findAll();
    }
    public Product guardarProduct(Product entrada){
        return productRepository.save(entrada);
    }
    public Product modificarProduct(long id,Product input){
        Product newProduct = getProduct(id).get();
        newProduct.setName(input.getName());
        newProduct.setCode(input.getCode());
        Product modificado = productRepository.save(newProduct);
        return modificado;
    }
    public Optional<Product> getProduct(long id){
        return productRepository.findById(id);
    }
    public void eliminarProduct(long id){
        productRepository.deleteById(id);
    }
}
