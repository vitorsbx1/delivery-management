package br.vitorsb.delivery_management_api.service.impl;

import br.vitorsb.delivery_management_api.config.exception.DeliveryNotFoundException;

import br.vitorsb.delivery_management_api.domain.AddressDelivery;
import br.vitorsb.delivery_management_api.domain.Customer;
import br.vitorsb.delivery_management_api.domain.Delivery;
import br.vitorsb.delivery_management_api.domain.dto.request.AddressRequest;
import br.vitorsb.delivery_management_api.domain.dto.request.CustomerRequest;
import br.vitorsb.delivery_management_api.domain.dto.request.DeliveryRequest;
import br.vitorsb.delivery_management_api.domain.dto.response.DeliveryResponse;
import br.vitorsb.delivery_management_api.domain.mapper.AddressDeliveryMapper;
import br.vitorsb.delivery_management_api.domain.mapper.CustomerMapper;
import br.vitorsb.delivery_management_api.domain.mapper.DeliveryMapper;
import br.vitorsb.delivery_management_api.repository.DeliveryRepository;
import br.vitorsb.delivery_management_api.service.AddressDeliveryService;
import br.vitorsb.delivery_management_api.service.CustomerService;
import br.vitorsb.delivery_management_api.service.DeliveryService;
import org.springframework.stereotype.Service;


@Service
public class DeliveryServiceImpl implements DeliveryService {


    private final DeliveryRepository deliveryRepository;
    private final DeliveryMapper deliveryMapper;
    private final CustomerService customerService;
    private final AddressDeliveryService addressDeliveryService;


    public DeliveryServiceImpl(DeliveryRepository deliveryRepository,
                               DeliveryMapper deliveryMapper,
                               CustomerService customerService, AddressDeliveryService addressDeliveryService) {
        this.deliveryRepository = deliveryRepository;
        this.deliveryMapper = deliveryMapper;
        this.customerService = customerService;
        this.addressDeliveryService = addressDeliveryService;
    }

    @Override
    public DeliveryResponse createDelivery(DeliveryRequest deliveryRequest) {

        Customer customer = findOrCreate(deliveryRequest.customer());
        AddressDelivery addressDelivery = findOrCreate(customer, deliveryRequest.addressDelivery());

        Delivery delivery = Delivery.builder()
                .packageQuantity(deliveryRequest.packageQuantity())
                .deliveryDeadline(deliveryRequest.deliveryDeadline())
                .customer(customer)
                .addressDelivery(addressDelivery)
                .build();

        delivery = deliveryRepository.save(delivery);

        return deliveryMapper.toResponse(delivery);

    }

    private AddressDelivery findOrCreate(Customer customer, AddressRequest addressRequest) {
        return addressDeliveryService.findOrCreate(customer,addressRequest);
    }

    private Customer findOrCreate(CustomerRequest customerRequest) {
        return customerService.findOrCreate(customerRequest);
    }

    @Override
    public DeliveryResponse getDeliveryById(Long id) {
        return deliveryRepository.findById(id)
                .map(deliveryMapper::toResponse)
                .orElseThrow(() -> new DeliveryNotFoundException("Delivery not found with id: " + id));
    }

    @Override
    public DeliveryResponse updateDelivery(Long id, DeliveryRequest deliveryRequest) {
        Delivery delivery = deliveryRepository.findById(id)
                .orElseThrow(() -> new DeliveryNotFoundException("Delivery not found with id: " + id));

        Customer updatedCustomer = findOrCreate(deliveryRequest.customer());
        AddressDelivery updatedAddress = findOrCreate(updatedCustomer, deliveryRequest.addressDelivery());

        deliveryMapper.updateEntityFromRequest(deliveryRequest, delivery);
        delivery.setCustomer(updatedCustomer);
        delivery.setAddressDelivery(updatedAddress);
        deliveryRepository.save(delivery);

        return deliveryMapper.toResponse(delivery);
    }

    @Override
    public void deleteDelivery(Long id) {
        Delivery deliveryExist = deliveryRepository.findById(id)
                .orElseThrow(() -> new DeliveryNotFoundException("Delivery not found with id: " + id));
        deliveryRepository.delete(deliveryExist);
    }



}
