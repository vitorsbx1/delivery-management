package br.vitorsb.delivery_management_api.domain.dto.response;

import jakarta.validation.constraints.NotBlank;

public record CustomerResponse(
        String name,
        String cpf
) {
}
