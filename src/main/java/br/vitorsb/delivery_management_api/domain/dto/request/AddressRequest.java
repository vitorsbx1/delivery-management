package br.vitorsb.delivery_management_api.domain.dto.request;

import jakarta.validation.constraints.NotBlank;

public record AddressRequest(

        @NotBlank(message = "CEP is required")
        String cep,

        @NotBlank(message = "UF is required")
        String uf,

        @NotBlank(message = "City is required")
        String city,

        @NotBlank(message = "Neighborhood is required")
        String neighborhood,

        @NotBlank(message = "Street is required")
        String street,

        @NotBlank(message = "Number is required")
        String number,

        String complement
) {
}
