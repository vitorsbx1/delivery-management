package br.vitorsb.delivery_management_api.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

public record CustomerRequest(
        @NotBlank(message = "Name is required")
        String name,
        @NotBlank(message = "CPF is required")
        @CPF
        String cpf
) {
}
