package br.vitorsb.delivery_management_api.service.impl;

import br.vitorsb.delivery_management_api.config.exception.DeliveryNotFoundException;

import br.vitorsb.delivery_management_api.domain.AddressDelivery;
import br.vitorsb.delivery_management_api.domain.Customer;
import br.vitorsb.delivery_management_api.domain.Delivery;
import br.vitorsb.delivery_management_api.domain.dto.AddressDTO;
import br.vitorsb.delivery_management_api.domain.dto.CustomerDTO;
import br.vitorsb.delivery_management_api.domain.dto.request.DeliveryRequest;
import br.vitorsb.delivery_management_api.domain.dto.response.DeliveryResponse;
import br.vitorsb.delivery_management_api.repository.AddressDeliveryRepository;
import br.vitorsb.delivery_management_api.repository.CustomerRepository;
import br.vitorsb.delivery_management_api.repository.DeliveryRepository;
import br.vitorsb.delivery_management_api.service.DeliveryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


@Service
public class DeliveryServiceImpl implements DeliveryService {

    private final ModelMapper modelMapper;
    private final DeliveryRepository deliveryRepository;
    private final CustomerRepository customerRepository;
    private final AddressDeliveryRepository addressDeliveryRepository;


    public DeliveryServiceImpl(ModelMapper modelMapper, DeliveryRepository deliveryRepository, CustomerRepository customerRepository, AddressDeliveryRepository addressDeliveryRepository) {
        this.modelMapper = modelMapper;
        this.deliveryRepository = deliveryRepository;
        this.customerRepository = customerRepository;
        this.addressDeliveryRepository = addressDeliveryRepository;
    }

    @Override
    public DeliveryResponse createDelivery(DeliveryRequest deliveryRequest) {

        Delivery delivery = buildDelivery(deliveryRequest);
        delivery = deliveryRepository.save(delivery);
        return buildDeliveryResponse(delivery);

    }

    @Override
    public DeliveryResponse getDeliveryById(Long id) {
        return deliveryRepository.findById(id)
                .map(this::buildDeliveryResponse)
                .orElseThrow(() -> new DeliveryNotFoundException("Delivery not found with id: " + id));
    }

    @Override
    public DeliveryResponse updateDelivery(Long id, DeliveryRequest deliveryRequest) {
        Delivery deliveryExist = deliveryRepository.findById(id)
            .orElseThrow(() -> new DeliveryNotFoundException("Delivery not found with id: " + id));

        return buildDeliveryResponse(deliveryExist);
    }

    @Override
    public void deleteDelivery(Long id) {
        Delivery deliveryExist = deliveryRepository.findById(id)
                .orElseThrow(() -> new DeliveryNotFoundException("Delivery not found with id: " + id));
        deliveryRepository.delete(deliveryExist);
    }

    public Delivery buildDelivery(DeliveryRequest deliveryRequest) {

        Customer customer = buildCustomer(deliveryRequest);
        AddressDelivery addressDelivery = buildAddressDelivery(deliveryRequest);


        Delivery delivery = Delivery.builder()
                .packageQuantity(deliveryRequest.packageQuantity())
                .deliveryDeadline(deliveryRequest.deliveryDeadline())
                .customer(customer)
                .addressDelivery(addressDelivery)
                .build();


        return delivery;
    }

    private AddressDelivery buildAddressDelivery(DeliveryRequest deliveryRequest) {
        AddressDelivery addressDelivery = AddressDelivery
                .builder()
                .cep(deliveryRequest.addressDelivery().cep())
                .uf(deliveryRequest.addressDelivery().uf())
                .number(deliveryRequest.addressDelivery().number())
                .city(deliveryRequest.addressDelivery().city())
                .street(deliveryRequest.addressDelivery().street())
                .neighborhood(deliveryRequest.addressDelivery().neighborhood())
                .complement(deliveryRequest.addressDelivery().complement())
                .build();
        return addressDeliveryRepository.save(addressDelivery);
    }

    private Customer buildCustomer(DeliveryRequest deliveryRequest) {
        Customer customer = Customer.builder()
                .cpf(deliveryRequest.customer().cpf())
                .name(deliveryRequest.customer().name())
                .build();

        return customerRepository.save(customer);
    }

    public DeliveryResponse buildDeliveryResponse(Delivery delivery){
         return new DeliveryResponse(
                delivery.getDeliveryId(),
                delivery.getPackageQuantity(),
                delivery.getDeliveryDeadline(),
                modelMapper.map(delivery.getCustomer(), CustomerDTO.class),
                modelMapper.map(delivery.getAddressDelivery(), AddressDTO.class)
        );
    }



}
