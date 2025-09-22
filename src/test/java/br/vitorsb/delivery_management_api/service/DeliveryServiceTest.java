package br.vitorsb.delivery_management_api.service;

import br.vitorsb.delivery_management_api.config.exception.DeliveryNotFoundException;
import br.vitorsb.delivery_management_api.domain.AddressDelivery;
import br.vitorsb.delivery_management_api.domain.Customer;
import br.vitorsb.delivery_management_api.domain.Delivery;
import br.vitorsb.delivery_management_api.domain.dto.request.AddressRequest;
import br.vitorsb.delivery_management_api.domain.dto.request.CustomerRequest;
import br.vitorsb.delivery_management_api.domain.dto.request.DeliveryRequest;
import br.vitorsb.delivery_management_api.domain.dto.response.AddressResponse;
import br.vitorsb.delivery_management_api.domain.dto.response.CustomerResponse;
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
    private LocalDateTime deliveryDeadline;

    @BeforeEach
    void setUp() {
        deliveryDeadline = LocalDateTime.now().plusDays(1);

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
                .deliveryDeadline(deliveryDeadline)
                .customer(customer)
                .addressDelivery(addressDelivery)
                .build();

        CustomerRequest customerRequest = new CustomerRequest("Igor", "1233123123");
        AddressRequest addressRequest = new AddressRequest("12345000", "SP", "Sao Paulo", "Centro",
                "Av. Brasil", "323", null);

        deliveryRequest = new DeliveryRequest(
                delivery.getPackageQuantity(),
                deliveryDeadline,
                customerRequest,
                addressRequest
        );

        deliveryResponse = new DeliveryResponse(
                delivery.getDeliveryId(),
                delivery.getPackageQuantity(),
                delivery.getDeliveryDeadline(),
                new CustomerResponse(customer.getName(), customer.getCpf()),
                new AddressResponse(
                        addressDelivery.getCep(),
                        addressDelivery.getUf(),
                        addressDelivery.getCity(),
                        addressDelivery.getNeighborhood(),
                        addressDelivery.getStreet(),
                        addressDelivery.getNumber(),
                        addressDelivery.getComplement()
                )
        );
    }

    @Test
    @DisplayName("Should create delivery successfully")
    void shouldCreateDeliverySuccessfully() {
        when(customerService.findOrCreate(any(CustomerRequest.class))).thenReturn(customer);
        when(addressDeliveryService.findOrCreate(any(Customer.class), any(AddressRequest.class))).thenReturn(addressDelivery);
        when(deliveryRepository.save(any(Delivery.class))).thenReturn(delivery);

        DeliveryResponse result = deliveryService.createDelivery(deliveryRequest);

        assertThat(result).isNotNull();
        assertThat(result.deliveryId()).isEqualTo(delivery.getDeliveryId());
        assertThat(result.packageQuantity()).isEqualTo(delivery.getPackageQuantity());
        assertThat(result.deliveryDeadline()).isEqualTo(delivery.getDeliveryDeadline());
        assertThat(result.customer().name()).isEqualTo(customer.getName());
        assertThat(result.customer().cpf()).isEqualTo(customer.getCpf());
        assertThat(result.addressDelivery().street()).isEqualTo(addressDelivery.getStreet());
        assertThat(result.addressDelivery().city()).isEqualTo(addressDelivery.getCity());

        verify(customerService).findOrCreate(any(CustomerRequest.class));
        verify(addressDeliveryService).findOrCreate(any(Customer.class), any(AddressRequest.class));
        verify(deliveryRepository).save(any(Delivery.class));
    }

    @Test
    @DisplayName("Should find delivery successfully")
    void shouldGetDeliveryByIdSuccessfully() {
        when(deliveryRepository.findById(delivery.getDeliveryId())).thenReturn(Optional.of(delivery));

        DeliveryResponse result = deliveryService.getDeliveryById(delivery.getDeliveryId());

        assertThat(result).isNotNull();
        assertThat(result.deliveryId()).isEqualTo(delivery.getDeliveryId());
        assertThat(result.packageQuantity()).isEqualTo(delivery.getPackageQuantity());
        assertThat(result.deliveryDeadline()).isEqualTo(delivery.getDeliveryDeadline());
        assertThat(result.customer()).isNotNull();
        assertThat(result.customer().name()).isEqualTo(customer.getName());
        assertThat(result.customer().cpf()).isEqualTo(customer.getCpf());
        assertThat(result.addressDelivery()).isNotNull();
        assertThat(result.addressDelivery().street()).isEqualTo(addressDelivery.getStreet());
        assertThat(result.addressDelivery().city()).isEqualTo(addressDelivery.getCity());
        assertThat(result.addressDelivery().cep()).isEqualTo(addressDelivery.getCep());
        assertThat(result.addressDelivery().uf()).isEqualTo(addressDelivery.getUf());
        assertThat(result.addressDelivery().neighborhood()).isEqualTo(addressDelivery.getNeighborhood());
        assertThat(result.addressDelivery().number()).isEqualTo(addressDelivery.getNumber());

        verify(deliveryRepository).findById(delivery.getDeliveryId());
    }

    @Test
    @DisplayName("Should throw DeliveryNotFoundException if delivery not found")
    void shouldThrowExceptionWhenDeliveryNotFound() {
        Long nonExistentId = 999L;
        when(deliveryRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        DeliveryNotFoundException exception = assertThrows(DeliveryNotFoundException.class,
                () -> deliveryService.getDeliveryById(nonExistentId));

        assertThat(exception.getMessage()).contains(String.valueOf(nonExistentId));
        verify(deliveryRepository).findById(nonExistentId);
    }

    @Test
    @DisplayName("Should update delivery successfully")
    void shouldUpdateDeliverySuccessfully() {
        Long deliveryId = 1L;
        CustomerRequest customerRequest = new CustomerRequest("Igor", "1233123123");
        AddressRequest addressRequest = new AddressRequest("12345000", "SP", "Sao Paulo", "Centro",
                "Rua Nova", "323", null);
        DeliveryRequest updateRequest = new DeliveryRequest(2,
                deliveryDeadline.plusDays(1),
                customerRequest,
                addressRequest);

        Customer updatedCustomer = Customer.builder()
                .customerId(2L)
                .name("Igor")
                .cpf("1233123123")
                .build();

        AddressDelivery updatedAddress = AddressDelivery.builder()
                .addressDeliveryId(2L)
                .cep("12345000")
                .uf("SP")
                .city("Sao Paulo")
                .neighborhood("Centro")
                .street("Rua Nova")
                .number("323")
                .customer(updatedCustomer)
                .build();

        Delivery updatedDelivery = Delivery.builder()
                .deliveryId(deliveryId)
                .packageQuantity(updateRequest.packageQuantity())
                .deliveryDeadline(updateRequest.deliveryDeadline())
                .customer(updatedCustomer)
                .addressDelivery(updatedAddress)
                .build();

        when(deliveryRepository.findById(deliveryId)).thenReturn(Optional.of(delivery));
        when(customerService.findOrCreate(any(CustomerRequest.class))).thenReturn(updatedCustomer);
        when(addressDeliveryService.findOrCreate(any(Customer.class), any(AddressRequest.class))).thenReturn(updatedAddress);
        when(deliveryRepository.save(any(Delivery.class))).thenReturn(updatedDelivery);

        DeliveryResponse result = deliveryService.updateDelivery(deliveryId, updateRequest);

        assertThat(result).isNotNull();
        assertThat(result.deliveryId()).isEqualTo(deliveryId);
        assertThat(result.packageQuantity()).isEqualTo(updateRequest.packageQuantity());
        assertThat(result.deliveryDeadline()).isEqualTo(updateRequest.deliveryDeadline());
        assertThat(result.customer().name()).isEqualTo(updatedCustomer.getName());
        assertThat(result.customer().cpf()).isEqualTo(updatedCustomer.getCpf());
        assertThat(result.addressDelivery().street()).isEqualTo(updatedAddress.getStreet());
        assertThat(result.addressDelivery().city()).isEqualTo(updatedAddress.getCity());

        verify(deliveryRepository).findById(deliveryId);
        verify(customerService).findOrCreate(any(CustomerRequest.class));
        verify(addressDeliveryService).findOrCreate(any(Customer.class), any(AddressRequest.class));
        verify(deliveryRepository).save(any(Delivery.class));
    }

    @Test
    @DisplayName("Should throw DeliveryNotFoundException when updating non-existent delivery")
    void shouldThrowExceptionWhenUpdatingNonExistentDelivery() {
        Long nonExistentId = 999L;
        when(deliveryRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        DeliveryNotFoundException exception = assertThrows(DeliveryNotFoundException.class,
                () -> deliveryService.updateDelivery(nonExistentId, deliveryRequest));

        assertThat(exception.getMessage()).contains(String.valueOf(nonExistentId));
        verify(deliveryRepository).findById(nonExistentId);
        verify(customerService, never()).findOrCreate(any());
        verify(addressDeliveryService, never()).findOrCreate(any(), any());
        verify(deliveryRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should delete delivery successfully")
    void shouldDeleteDeliverySuccessfully() {
        when(deliveryRepository.findById(delivery.getDeliveryId())).thenReturn(Optional.of(delivery));
        doNothing().when(deliveryRepository).delete(any(Delivery.class));

        deliveryService.deleteDelivery(delivery.getDeliveryId());

        verify(deliveryRepository).findById(delivery.getDeliveryId());
        verify(deliveryRepository).delete(delivery);
    }

    @Test
    @DisplayName("Should throw DeliveryNotFoundException when deleting non-existent delivery")
    void shouldThrowExceptionWhenDeletingNonExistentDelivery() {
        Long nonExistentId = 999L;
        when(deliveryRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        DeliveryNotFoundException exception = assertThrows(DeliveryNotFoundException.class,
                () -> deliveryService.deleteDelivery(nonExistentId));

        assertThat(exception.getMessage()).contains(String.valueOf(nonExistentId));
        verify(deliveryRepository).findById(nonExistentId);
        verify(deliveryRepository, never()).delete(any());
    }
}