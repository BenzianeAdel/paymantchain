/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.billing.common;

import com.example.billing.dto.BillingResponse;
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
public interface BillingResponseMapper {

    @Mappings({
        @Mapping(source = "customerId", target = "customer"),
        @Mapping(source = "id", target = "invoiceId")})
    BillingResponse BillingToBillingRespose(Billing source);

    List<BillingResponse> BillingListToBillingResposeList(List<Billing> source);

    @InheritInverseConfiguration
    Billing BillingResponseToBilling(BillingResponse srr);

    @InheritInverseConfiguration
    List<Billing> BillingResponseToBillingList(List<BillingResponse> source);

}
