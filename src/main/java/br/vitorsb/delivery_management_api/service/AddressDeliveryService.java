package br.vitorsb.delivery_management_api.service;

import br.vitorsb.delivery_management_api.domain.AddressDelivery;
import br.vitorsb.delivery_management_api.domain.Customer;
import br.vitorsb.delivery_management_api.domain.dto.request.AddressRequest;

import java.util.List;

public interface AddressDeliveryService {

    AddressDelivery findOrCreate(Customer customer, AddressRequest addressRequest);
    List<AddressDelivery> findByCustomerId(Long customerId);
    AddressDelivery findByCepAndNumberAndCustomerId(String cep, String number, Long customerId);
}
