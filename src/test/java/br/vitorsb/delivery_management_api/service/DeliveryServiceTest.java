package br.vitorsb.delivery_management_api.service;

import br.vitorsb.delivery_management_api.config.exception.DeliveryNotFoundException;
import br.vitorsb.delivery_management_api.domain.AddressDelivery;
import br.vitorsb.delivery_management_api.domain.Customer;
import br.vitorsb.delivery_management_api.domain.Delivery;
import br.vitorsb.delivery_management_api.domain.dto.request.AddressRequest;
import br.vitorsb.delivery_management_api.domain.dto.request.CustomerRequest;
import br.vitorsb.delivery_management_api.domain.dto.request.DeliveryRequest;
import br.vitorsb.delivery_management_api.domain.dto.response.AddressResponse;
import br.vitorsb.delivery_management_api.domain.dto.response.DeliveryResponse;
import br.vitorsb.delivery_management_api.domain.mapper.DeliveryMapper;
import br.vitorsb.delivery_management_api.repository.DeliveryRepository;
import br.vitorsb.delivery_management_api.service.impl.DeliveryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeliveryServiceTest {

    @Mock
    private DeliveryRepository deliveryRepository;
    @Mock
    private DeliveryMapper deliveryMapper;

    @Mock
    private CustomerService customerService;

    @Mock
    private AddressDeliveryService addressDeliveryService;

    @InjectMocks
    private DeliveryServiceImpl deliveryService;

    private Customer customer;
    private AddressDelivery addressDelivery;
    private Delivery delivery;
    private DeliveryRequest deliveryRequest;
    private DeliveryResponse deliveryResponse;

    @BeforeEach
    void setUp() {

        customer = Customer.builder()
                .customerId(1L)
                .name("Vitor")
                .cpf("11122233344")
                .build();
        addressDelivery = AddressDelivery.builder()
                .addressDeliveryId(1L)
                .cep("12345000")
                .uf("MG")
                .city("Uberlandia")
                .neighborhood("Centro")
                .street("Av. Brasil")
                .number("223")
                .customer(customer)
                .build();


        delivery = Delivery.builder()
                .deliveryId(1L)
                .packageQuantity(1)
                .deliveryDeadline(LocalDateTime.now().plusDays(1))
                .customer(customer)
                .addressDelivery(addressDelivery)
                .build();

        CustomerRequest customerRequest = new CustomerRequest("Igor", "1233123123");
        AddressRequest addressRequest = new AddressRequest("12345000", "SP", "Sao Paulo", "Centro",
                "Av. Brasil", "323", null);

        deliveryRequest = new DeliveryRequest( delivery.getPackageQuantity(),
                delivery.getDeliveryDeadline(),
                customerRequest,
                addressRequest
        );

        deliveryResponse = new DeliveryResponse(delivery.getDeliveryId(), delivery.getPackageQuantity(),
                delivery.getDeliveryDeadline(),
                null,
                null
        );
    }

    @Test
    @DisplayName("SHould create delivery successfully")
    void shouldCreateDeliverySuccessfully() {
        when(customerService.findOrCreate(any(CustomerRequest.class))).thenReturn(customer);

        when(addressDeliveryService.findOrCreate(any(Customer.class), any(AddressRequest.class))).thenReturn(addressDelivery);

        when(deliveryRepository.save(any(Delivery.class))).thenReturn(delivery);

        when(deliveryMapper.toResponse(any(Delivery.class))).thenReturn(deliveryResponse);

        DeliveryResponse result = deliveryService.createDelivery(deliveryRequest);

        assertThat(result).isNotNull();
        assertThat(result.deliveryId()).isEqualTo(deliveryResponse.deliveryId());
        assertThat(result.packageQuantity()).isEqualTo(deliveryResponse.packageQuantity());

        verify(customerService, times(1)).findOrCreate(any(CustomerRequest.class));
        verify(addressDeliveryService, times(1)).findOrCreate(any(Customer.class), any(AddressRequest.class));
        verify(deliveryRepository, times(1)).save(any(Delivery.class));
        verify(deliveryMapper, times(1)).toResponse(any(Delivery.class));
    }

    @Test
    @DisplayName("Should find delivery sucess")
    void shouldGetDeliveryByIdSuccessfully() {
        when(deliveryRepository.findById(delivery.getDeliveryId())).thenReturn(Optional.of(delivery));
        when(deliveryMapper.toResponse(any(Delivery.class))).thenReturn(deliveryResponse);

        DeliveryResponse result = deliveryService.getDeliveryById(delivery.getDeliveryId());

        assertThat(result).isNotNull();
        assertThat(result.deliveryId()).isEqualTo(deliveryResponse.deliveryId());
        verify(deliveryRepository, times(1)).findById(delivery.getDeliveryId());
        verify(deliveryMapper, times(1)).toResponse(delivery);
    }

    @Test
    @DisplayName("Should thorw DeliveryNotFOundException if delivery not found")
    void shouldThrowExceptionWhenDeliveryNotFound() {
        when(deliveryRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(DeliveryNotFoundException.class, () -> deliveryService.getDeliveryById(2L));
        verify(deliveryRepository, times(1)).findById(2L);
        verify(deliveryMapper, never()).toResponse(any(Delivery.class));
    }

    @Test
    @DisplayName("Should update delivery sucess")
    void shouldUpdateDeliverySuccessfully() {
        when(deliveryRepository.findById(delivery.getDeliveryId())).thenReturn(Optional.of(delivery));
        when(customerService.findOrCreate(any(CustomerRequest.class))).thenReturn(customer);
        when(addressDeliveryService.findOrCreate(any(Customer.class), any(AddressRequest.class))).thenReturn(addressDelivery);
        when(deliveryRepository.save(any(Delivery.class))).thenReturn(delivery);
        when(deliveryMapper.toResponse(any(Delivery.class))).thenReturn(deliveryResponse);

        doNothing().when(deliveryMapper).updateEntityFromRequest(any(DeliveryRequest.class), any(Delivery.class));

        DeliveryResponse result = deliveryService.updateDelivery(delivery.getDeliveryId(), deliveryRequest);

        assertThat(result).isNotNull();
        assertThat(result.deliveryId()).isEqualTo(deliveryResponse.deliveryId());
        verify(deliveryRepository, times(1)).findById(delivery.getDeliveryId());
        verify(customerService, times(1)).findOrCreate(any(CustomerRequest.class));
        verify(addressDeliveryService, times(1)).findOrCreate(any(Customer.class), any(AddressRequest.class));
        verify(deliveryMapper, times(1)).updateEntityFromRequest(deliveryRequest, delivery);
        verify(deliveryRepository, times(1)).save(delivery);
        verify(deliveryMapper, times(1)).toResponse(delivery);
    }

    @Test
    @DisplayName("Should delete delivery sucess")
    void shouldDeleteDeliverySuccessfully() {
        when(deliveryRepository.findById(delivery.getDeliveryId())).thenReturn(Optional.of(delivery));
        doNothing().when(deliveryRepository).delete(any(Delivery.class));

        deliveryService.deleteDelivery(delivery.getDeliveryId());

        verify(deliveryRepository, times(1)).findById(delivery.getDeliveryId());
        verify(deliveryRepository, times(1)).delete(delivery);
    }

    @Test
    @DisplayName("Should throw DeliveryNotfoundExcpetion if delivery not found")
    void shouldThrowExceptionWhenDeletingNonExistentDelivery() {
        when(deliveryRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(DeliveryNotFoundException.class, () -> deliveryService.deleteDelivery(2L));
        verify(deliveryRepository, times(1)).findById(2L);
        verify(deliveryRepository, never()).delete(any(Delivery.class));
    }

}