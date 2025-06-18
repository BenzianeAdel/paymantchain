/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.billing.common;

import com.example.billing.dto.BillingRequest;
import com.example.billing.entities.Billing;
import java.util.List;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 *
 * @author adelb
 */
@Mapper(componentModel = "spring")
public interface BillingRequestMapper {

    @Mappings({
        @Mapping(source = "customer", target = "customerId")})
    Billing BillingRequestToBilling(BillingRequest source);

    List<Billing> BillingRequestListToBillingList(List<BillingRequest> source);

    @InheritInverseConfiguration
    BillingRequest BillingToBillingRequest(Billing source);

    @InheritInverseConfiguration
    List<BillingRequest> BillingListToBillingRequestList(List<Billing> source);

}
