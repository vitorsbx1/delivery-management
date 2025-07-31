package br.vitorsb.delivery_management_api.service;

import br.vitorsb.delivery_management_api.domain.dto.request.DeliveryRequest;
import br.vitorsb.delivery_management_api.domain.dto.response.DeliveryResponse;

public interface DeliveryService {
    DeliveryResponse createDelivery(DeliveryRequest deliveryRequest);
    DeliveryResponse getDeliveryById(Long id);
    DeliveryResponse updateDelivery(Long id, DeliveryRequest deliveryRequest);
    void deleteDelivery(Long id);
}
