package br.vitorsb.delivery_management_api.domain.dto.request;

import br.vitorsb.delivery_management_api.domain.dto.AddressDTO;
import br.vitorsb.delivery_management_api.domain.dto.CustomerDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DeliveryRequest(

        @NotBlank(message = "Package quantity is required")
        Integer packageQuantity,

        @NotBlank(message = "Delivery deadline is required")
        LocalDateTime deliveryDeadline,

        @Valid
        @NotNull(message = "Customer is required")
        CustomerDTO customer,

        @Valid
        @NotNull(message = "Address Delivery is required")
        AddressDTO addressDelivery) {
}
