package br.vitorsb.delivery_management_api.service.impl;

import br.vitorsb.delivery_management_api.config.exception.AddressDeliveryNotFoundException;
import br.vitorsb.delivery_management_api.domain.AddressDelivery;
import br.vitorsb.delivery_management_api.domain.Customer;
import br.vitorsb.delivery_management_api.domain.dto.request.AddressRequest;
import br.vitorsb.delivery_management_api.domain.mapper.AddressDeliveryMapper;
import br.vitorsb.delivery_management_api.repository.AddressDeliveryRepository;
import br.vitorsb.delivery_management_api.service.AddressDeliveryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressDeliveryServiceImpl implements AddressDeliveryService {

    private final AddressDeliveryRepository addressDeliveryRepository;

    public AddressDeliveryServiceImpl(AddressDeliveryRepository addressDeliveryRepository) {
        this.addressDeliveryRepository = addressDeliveryRepository;
    }


    @Override
    public AddressDelivery findOrCreate(Customer customer, AddressRequest addressRequest) {

        return addressDeliveryRepository.findByCepAndNumberAndCustomerCustomerId(
                        addressRequest.cep(),
                        addressRequest.number(),
                        customer.getCustomerId())
                .orElseGet(() -> {
                    AddressDelivery addressDelivery = AddressDeliveryMapper.toEntity(addressRequest);
                    addressDelivery.setCustomer(customer);
                    return addressDeliveryRepository.save(addressDelivery);
                });
    }

    @Override
    public List<AddressDelivery> findByCustomerId(Long customerId) {
        return addressDeliveryRepository.findByCustomerCustomerId(customerId);
    }

    @Override
    public AddressDelivery findByCepAndNumberAndCustomerId(String cep, String number, Long customerId) {
        return addressDeliveryRepository.findByCepAndNumberAndCustomerCustomerId(cep, number, customerId)
                .orElseThrow(() -> new AddressDeliveryNotFoundException("Address not found for the given customer"));
    }

}
