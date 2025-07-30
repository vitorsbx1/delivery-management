package br.vitorsb.delivery_management_api.domain.dto.response;

import br.vitorsb.delivery_management_api.domain.dto.AddressDTO;
import br.vitorsb.delivery_management_api.domain.dto.CustomerDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DeliveryResponse(
        Long id,
        Integer packageQuantity,
        LocalDateTime deliveryDeadline,
        CustomerDTO customer,
        AddressDTO addressDelivery) {
}
