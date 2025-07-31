package br.vitorsb.delivery_management_api.domain.dto.response;

import jakarta.validation.constraints.NotBlank;

public record AddressResponse(


        String cep,
        String uf,
        String city,
        String neighborhood,
        String street,
        String number,
        String complement
) {
}
