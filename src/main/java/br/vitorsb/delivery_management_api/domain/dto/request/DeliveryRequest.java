package br.vitorsb.delivery_management_api.domain.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DeliveryRequest(

        @NotNull(message = "Package quantity is required")
        Integer packageQuantity,

        @NotNull(message = "Delivery deadline is required")
        LocalDateTime deliveryDeadline,

        @Valid
        @NotNull(message = "Customer is required")
        CustomerRequest customer,

        @Valid
        @NotNull(message = "Address Delivery is required")
        AddressRequest addressDelivery) {
}
