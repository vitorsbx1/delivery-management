package br.vitorsb.delivery_management_api.domain.dto.response;

import java.time.LocalDateTime;

public record DeliveryResponse(
        Long deliveryId,
        Integer packageQuantity,
        LocalDateTime deliveryDeadline,
        CustomerResponse customer,
        AddressResponse addressDelivery) {
}
