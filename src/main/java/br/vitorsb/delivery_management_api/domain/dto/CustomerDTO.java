package br.vitorsb.delivery_management_api.domain.dto;

import jakarta.validation.constraints.NotBlank;

public record CustomerDTO(

        @NotBlank(message = "Name is required")
        String name,
        @NotBlank(message = "CPF is required")
        String cpf
) {
}
