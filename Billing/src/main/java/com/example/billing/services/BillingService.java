/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Service.java to edit this template
 */
package com.example.billing.services;

import com.example.billing.common.BillingRequestMapper;
import com.example.billing.common.BillingResponseMapper;
import com.example.billing.dto.BillingRequest;
import com.example.billing.dto.BillingResponse;
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

    @Autowired
    BillingRequestMapper brm;

    @Autowired
    BillingResponseMapper brspm;

    public List<BillingResponse> getBillingAll() {
        return brspm.BillingListToBillingResposeList(billingRepository.findAll());
    }

    public Optional<BillingResponse> getBilling(long id) {
        return billingRepository.findById(id)
                .map(brspm::BillingToBillingRespose);
    }

    public BillingResponse modificarBilling(long id, BillingRequest input) {
        return billingRepository.findById(id)
                .map(billing -> {
                    billing.setAmount(input.getAmount());
                    billing.setDetail(input.getDetail());
                    billing.setNumber(input.getNumber());
                    Billing actualizado = billingRepository.save(billing);
                    return brspm.BillingToBillingRespose(actualizado);
                })
                .orElse(null);
    }

    public boolean eliminarBilling(long id) {
        if (billingRepository.existsById(id)) {
            billingRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public BillingResponse guardarBilling(BillingRequest entrada) {
        return brspm.BillingToBillingRespose(billingRepository.save(brm.BillingRequestToBilling(entrada)));
    }

}
