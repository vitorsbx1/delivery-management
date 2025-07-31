package br.vitorsb.delivery_management_api.service.impl;

import br.vitorsb.delivery_management_api.config.exception.CustomerNotFoundException;
import br.vitorsb.delivery_management_api.domain.AddressDelivery;
import br.vitorsb.delivery_management_api.domain.Customer;
import br.vitorsb.delivery_management_api.domain.dto.request.CustomerRequest;
import br.vitorsb.delivery_management_api.domain.mapper.CustomerMapper;
import br.vitorsb.delivery_management_api.repository.AddressDeliveryRepository;
import br.vitorsb.delivery_management_api.repository.CustomerRepository;
import br.vitorsb.delivery_management_api.service.AddressDeliveryService;
import br.vitorsb.delivery_management_api.service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }


    @Override
    public Customer findOrCreate(CustomerRequest customerRequest) {
        return customerRepository.findByCpf(customerRequest.cpf())
                .orElseGet(() -> {
                    Customer customer = customerMapper.toEntity(customerRequest);
                    return customerRepository.save(customer);
                });
    }

    @Override
    public Customer findById(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + customerId));
    }

}
