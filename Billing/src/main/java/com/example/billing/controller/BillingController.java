/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */
package com.example.billing.controller;

import com.example.billing.dto.BillingRequest;
import com.example.billing.dto.BillingResponse;
import com.example.billing.entities.Billing;
import com.example.billing.services.BillingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(description = "Return all invoices bundled into Response", summary = "Return 204 if no data found")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Exito"),
        @ApiResponse(responseCode = "500", description = "Internal error")})
    @GetMapping()
    public ResponseEntity<List<BillingResponse>> findAll() {
        List<BillingResponse> billings = billingService.getBillingAll();
        return billings.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(billings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BillingResponse> get(@PathVariable("id") long id) {
        Optional<BillingResponse> billing = billingService.getBilling(id);
        return billing.isPresent() ? ResponseEntity.ok(billing.get()) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<BillingResponse> put(@PathVariable("id") long id, @RequestBody BillingRequest input) {
        BillingResponse br = billingService.modificarBilling(id, input);
        return br != null ? ResponseEntity.ok(br) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<BillingResponse> post(@RequestBody BillingRequest input) {
        BillingResponse billingResponse = billingService.guardarBilling(input);
        return billingResponse != null
                ? ResponseEntity.status(HttpStatus.CREATED).body(billingResponse)
                : ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        return billingService.eliminarBilling(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
