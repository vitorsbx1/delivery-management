package br.vitorsb.delivery_management_api.service;

import br.vitorsb.delivery_management_api.domain.AddressDelivery;
import br.vitorsb.delivery_management_api.domain.Customer;
import br.vitorsb.delivery_management_api.domain.dto.request.CustomerRequest;

import java.util.List;

public interface CustomerService {

    Customer findOrCreate(CustomerRequest customerRequest);
    Customer findById(Long customerId);
}
