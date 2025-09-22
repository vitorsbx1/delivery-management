package br.vitorsb.delivery_management_api.service;

import br.vitorsb.delivery_management_api.domain.AddressDelivery;
import br.vitorsb.delivery_management_api.domain.Customer;
import br.vitorsb.delivery_management_api.domain.dto.request.AddressRequest;
import br.vitorsb.delivery_management_api.repository.AddressDeliveryRepository;
import br.vitorsb.delivery_management_api.service.impl.AddressDeliveryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressDeliveryServiceTest {

    @Mock
    private AddressDeliveryRepository addressDeliveryRepository;

    @InjectMocks
    private AddressDeliveryServiceImpl addressDeliveryService;

    private Customer customer;
    private AddressDelivery addressDelivery;
    private AddressRequest addressRequest;

    @BeforeEach
    void setUp() {
        customer = Customer.builder()
                .customerId(1L)
                .name("Vitor")
                .cpf("66666666666")
                .build();

        addressDelivery = AddressDelivery.builder()
                .addressDeliveryId(1L)
                .cep("12345000")
                .uf("SP")
                .city("São Paulo")
                .neighborhood("Centro")
                .street("Rua do Teste")
                .number("123")
                .customer(customer)
                .build();

        addressRequest = new AddressRequest(
                "12345000", "SP", "São Paulo", "Centro", "Rua do Teste", "123", null
        );
    }

    @Test
    @DisplayName("Should find or create address when exists")
    void shouldFindOrCreateAddressWhenExistsForCustomer() {
        when(addressDeliveryRepository.findByCepAndNumberAndCustomerCustomerId(
                addressRequest.cep(), addressRequest.number(), customer.getCustomerId()))
                .thenReturn(Optional.of(addressDelivery));

        AddressDelivery result = addressDeliveryService.findOrCreate(customer, addressRequest);

        assertThat(result).isNotNull();
        assertThat(result.getAddressDeliveryId()).isEqualTo(addressDelivery.getAddressDeliveryId());
        assertThat(result.getCep()).isEqualTo(addressDelivery.getCep());
        assertThat(result.getStreet()).isEqualTo(addressDelivery.getStreet());
        assertThat(result.getNumber()).isEqualTo(addressDelivery.getNumber());
        assertThat(result.getCustomer()).isEqualTo(customer);

        verify(addressDeliveryRepository).findByCepAndNumberAndCustomerCustomerId(
                addressRequest.cep(), addressRequest.number(), customer.getCustomerId());
        verify(addressDeliveryRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should find or create address when not exists")
    void shouldFindOrCreateAddressWhenNotExistsForCustomer() {
        when(addressDeliveryRepository.findByCepAndNumberAndCustomerCustomerId(
                addressRequest.cep(), addressRequest.number(), customer.getCustomerId()))
                .thenReturn(Optional.empty());
        when(addressDeliveryRepository.save(any(AddressDelivery.class))).thenReturn(addressDelivery);

        AddressDelivery result = addressDeliveryService.findOrCreate(customer, addressRequest);

        assertThat(result).isNotNull();
        assertThat(result.getAddressDeliveryId()).isEqualTo(addressDelivery.getAddressDeliveryId());
        assertThat(result.getCep()).isEqualTo(addressDelivery.getCep());
        assertThat(result.getStreet()).isEqualTo(addressDelivery.getStreet());
        assertThat(result.getNumber()).isEqualTo(addressDelivery.getNumber());
        assertThat(result.getCustomer()).isEqualTo(customer);

        verify(addressDeliveryRepository).findByCepAndNumberAndCustomerCustomerId(
                addressRequest.cep(), addressRequest.number(), customer.getCustomerId());
        verify(addressDeliveryRepository).save(any());
    }

    @Test
    @DisplayName("Should find addresses by customer ID successfully")
    void shouldFindAddressesByCustomerIdSuccessfully() {
        List<AddressDelivery> addresses = Arrays.asList(addressDelivery);
        when(addressDeliveryRepository.findByCustomerCustomerId(customer.getCustomerId())).thenReturn(addresses);

        List<AddressDelivery> result = addressDeliveryService.findByCustomerId(customer.getCustomerId());

        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getAddressDeliveryId()).isEqualTo(addressDelivery.getAddressDeliveryId());
        assertThat(result.get(0).getCep()).isEqualTo(addressDelivery.getCep());
        assertThat(result.get(0).getStreet()).isEqualTo(addressDelivery.getStreet());

        verify(addressDeliveryRepository).findByCustomerCustomerId(customer.getCustomerId());
    }

    @Test
    @DisplayName("Should return empty list when no addresses found for customer")
    void shouldReturnEmptyListWhenNoAddressesFound() {
        when(addressDeliveryRepository.findByCustomerCustomerId(customer.getCustomerId()))
                .thenReturn(Collections.emptyList());

        List<AddressDelivery> result = addressDeliveryService.findByCustomerId(customer.getCustomerId());

        assertThat(result).isNotNull();
        assertThat(result).isEmpty();

        verify(addressDeliveryRepository).findByCustomerCustomerId(customer.getCustomerId());
    }

    @Test
    @DisplayName("Should find address by CEP, number and customer ID successfully")
    void shouldFindAddressByCepAndNumberAndCustomerIdSuccessfully() {
        when(addressDeliveryRepository.findByCepAndNumberAndCustomerCustomerId(
                addressRequest.cep(), addressRequest.number(), customer.getCustomerId()))
                .thenReturn(Optional.of(addressDelivery));

        AddressDelivery result = addressDeliveryService.findByCepAndNumberAndCustomerId(
                addressRequest.cep(), addressRequest.number(), customer.getCustomerId());

        assertThat(result).isNotNull();
        assertThat(result.getAddressDeliveryId()).isEqualTo(addressDelivery.getAddressDeliveryId());
        assertThat(result.getCep()).isEqualTo(addressDelivery.getCep());
        assertThat(result.getStreet()).isEqualTo(addressDelivery.getStreet());
        assertThat(result.getNumber()).isEqualTo(addressDelivery.getNumber());

        verify(addressDeliveryRepository).findByCepAndNumberAndCustomerCustomerId(
                addressRequest.cep(), addressRequest.number(), customer.getCustomerId());
    }





}