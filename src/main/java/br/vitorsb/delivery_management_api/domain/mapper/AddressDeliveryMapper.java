package br.vitorsb.delivery_management_api.domain.mapper;

import br.vitorsb.delivery_management_api.domain.AddressDelivery;
import br.vitorsb.delivery_management_api.domain.dto.request.AddressRequest;
import br.vitorsb.delivery_management_api.domain.dto.response.AddressResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressDeliveryMapper {


    AddressResponse toDTO(AddressDelivery addressDelivery);

    AddressDelivery toEntity(AddressRequest addressDTO);
}
