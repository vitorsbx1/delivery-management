package br.vitorsb.delivery_management_api.domain.mapper;

import br.vitorsb.delivery_management_api.domain.Delivery;
import br.vitorsb.delivery_management_api.domain.dto.request.DeliveryRequest;
import br.vitorsb.delivery_management_api.domain.dto.response.DeliveryResponse;

public class DeliveryMapper {


    public static DeliveryResponse toResponse(Delivery delivery){
        if (delivery == null) {
            return null;
        }

        return new DeliveryResponse(delivery.getDeliveryId(),
                delivery.getPackageQuantity(),
                delivery.getDeliveryDeadline(),
                CustomerMapper.toDTO(delivery.getCustomer()),
                AddressDeliveryMapper.toDTO(delivery.getAddressDelivery()));
    }

    public static Delivery toEntity(DeliveryRequest deliveryRequest){
        if (deliveryRequest == null) {
            return null;
        }

        return Delivery.builder()
                .packageQuantity(deliveryRequest.packageQuantity())
                .deliveryDeadline(deliveryRequest.deliveryDeadline())
                .customer(CustomerMapper.toEntity(deliveryRequest.customer()))
                .addressDelivery(AddressDeliveryMapper.toEntity(deliveryRequest.addressDelivery()))
                .build();
    }


    public static void updateEntityFromRequest(DeliveryRequest deliveryRequest, Delivery entity){
        if ( deliveryRequest == null ) {
            return;
        }

        entity.setPackageQuantity( deliveryRequest.packageQuantity() );
        entity.setDeliveryDeadline( deliveryRequest.deliveryDeadline() );
        entity.setCustomer( CustomerMapper.toEntity( deliveryRequest.customer() ) );
        entity.setAddressDelivery( AddressDeliveryMapper.toEntity( deliveryRequest.addressDelivery() ) );
    }

}
