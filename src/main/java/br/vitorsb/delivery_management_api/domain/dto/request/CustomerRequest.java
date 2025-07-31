package br.vitorsb.delivery_management_api.domain.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CustomerRequest(
        @NotBlank(message = "Name is required")
        String name,
        @NotBlank(message = "CPF is required")
        String cpf
) {
}
