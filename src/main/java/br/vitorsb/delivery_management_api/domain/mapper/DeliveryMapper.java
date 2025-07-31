package br.vitorsb.delivery_management_api.domain.mapper;

import br.vitorsb.delivery_management_api.domain.Delivery;
import br.vitorsb.delivery_management_api.domain.dto.request.DeliveryRequest;
import br.vitorsb.delivery_management_api.domain.dto.response.DeliveryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {CustomerMapper.class, AddressDeliveryMapper.class})
public interface DeliveryMapper {


    DeliveryResponse toResponse(Delivery delivery);
    Delivery toEntity(DeliveryRequest deliveryRequest);

    @Mapping(target = "deliveryId", ignore = true)
    void updateEntityFromRequest(DeliveryRequest deliveryRequest, @MappingTarget Delivery entity);

}
