package br.vitorsb.delivery_management_api.domain.mapper;

import br.vitorsb.delivery_management_api.domain.AddressDelivery;
import br.vitorsb.delivery_management_api.domain.dto.request.AddressRequest;
import br.vitorsb.delivery_management_api.domain.dto.response.AddressResponse;

public class AddressDeliveryMapper {

    public static AddressResponse toDTO(AddressDelivery addressDelivery){
        if(addressDelivery == null){
            return null;
        }

        return new AddressResponse(
                addressDelivery.getCep(),
                addressDelivery.getUf(),
                addressDelivery.getCity(),
                addressDelivery.getNeighborhood(),
                addressDelivery.getStreet(),
                addressDelivery.getNumber(),
                addressDelivery.getComplement()
        );
    }

    public static AddressDelivery toEntity(AddressRequest addressDTO){
        if (addressDTO == null){
            return null;
        }
        return AddressDelivery.builder()
                .cep(addressDTO.cep())
                .uf(addressDTO.uf())
                .city(addressDTO.city())
                .neighborhood(addressDTO.neighborhood())
                .street(addressDTO.street())
                .number(addressDTO.number())
                .complement(addressDTO.complement())
                .build();
    }
}
