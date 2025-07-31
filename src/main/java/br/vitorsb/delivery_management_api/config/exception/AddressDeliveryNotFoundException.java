package br.vitorsb.delivery_management_api.config.exception;

public class AddressDeliveryNotFoundException extends RuntimeException {

    public AddressDeliveryNotFoundException(String message) {
        super(message);
    }
}
