package br.vitorsb.delivery_management_api.service.impl;

import br.vitorsb.delivery_management_api.domain.AddressDelivery;
import br.vitorsb.delivery_management_api.domain.Customer;
import br.vitorsb.delivery_management_api.domain.Delivery;
import br.vitorsb.delivery_management_api.domain.dto.request.DeliveryRequest;
import br.vitorsb.delivery_management_api.domain.dto.response.DeliveryResponse;
import br.vitorsb.delivery_management_api.repository.DeliveryRepository;
import br.vitorsb.delivery_management_api.service.DeliveryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class DeliveryServiceImpl implements DeliveryService {

    private final ModelMapper modelMapper;
    private final DeliveryRepository deliveryRepository;

    public DeliveryServiceImpl(ModelMapper modelMapper, DeliveryRepository deliveryRepository) {
        this.modelMapper = modelMapper;
        this.deliveryRepository = deliveryRepository;
    }

    @Override
    public DeliveryResponse createDelivery(DeliveryRequest deliveryRequest) {

        Delivery delivery = buildDelivery(deliveryRequest);
        delivery = deliveryRepository.save(delivery);
        return buildDeliveryResponse(delivery);

    }

    public Delivery buildDelivery(DeliveryRequest deliveryRequest) {
        return modelMapper.map(deliveryRequest, Delivery.class);
    }

    public DeliveryResponse buildDeliveryResponse(Delivery delivery){
        return modelMapper.map(delivery, DeliveryResponse.class);
    }



}
