package br.vitorsb.delivery_management_api.service;

import br.vitorsb.delivery_management_api.config.exception.AddressDeliveryNotFoundException;
import br.vitorsb.delivery_management_api.domain.AddressDelivery;
import br.vitorsb.delivery_management_api.domain.Customer;
import br.vitorsb.delivery_management_api.domain.dto.request.AddressRequest;
import br.vitorsb.delivery_management_api.domain.mapper.AddressDeliveryMapper;
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
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressDeliveryServiceTest {

    @Mock
    private AddressDeliveryRepository addressDeliveryRepository;
    @Mock
    private AddressDeliveryMapper addressDeliveryMapper;

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
    @DisplayName("should find or create address")
    void shouldFindOrCreateAddressWhenExistsForCustomer() {
        when(addressDeliveryRepository.findByCepAndNumberAndCustomerCustomerId(
                addressRequest.cep(), addressRequest.number(), customer.getCustomerId()))
                .thenReturn(Optional.of(addressDelivery));

        AddressDelivery result = addressDeliveryService.findOrCreate(customer, addressRequest);

        assertThat(result).isNotNull();
        assertThat(result.getAddressDeliveryId()).isEqualTo(addressDelivery.getAddressDeliveryId());
        verify(addressDeliveryRepository, times(1)).findByCepAndNumberAndCustomerCustomerId(
                addressRequest.cep(), addressRequest.number(), customer.getCustomerId());
        verify(addressDeliveryRepository, never()).save(any(AddressDelivery.class));
        verify(addressDeliveryMapper, never()).toEntity(any(AddressRequest.class));
    }

    @Test
    @DisplayName("should find or create address when address not exist ")
    void shouldFindOrCreateAddressWhenNotExistsForCustomer() {
        when(addressDeliveryRepository.findByCepAndNumberAndCustomerCustomerId(
                addressRequest.cep(), addressRequest.number(), customer.getCustomerId()))
                .thenReturn(Optional.empty());
        when(addressDeliveryMapper.toEntity(addressRequest)).thenReturn(addressDelivery);
        when(addressDeliveryRepository.save(any(AddressDelivery.class))).thenReturn(addressDelivery);

        AddressDelivery result = addressDeliveryService.findOrCreate(customer, addressRequest);

        assertThat(result).isNotNull();
        assertThat(result.getAddressDeliveryId()).isEqualTo(addressDelivery.getAddressDeliveryId());
        verify(addressDeliveryRepository, times(1)).findByCepAndNumberAndCustomerCustomerId(
                addressRequest.cep(), addressRequest.number(), customer.getCustomerId());
        verify(addressDeliveryMapper, times(1)).toEntity(addressRequest);
        verify(addressDeliveryRepository, times(1)).save(any(AddressDelivery.class));
    }

    @Test
    @DisplayName("Should find addresses by customer ID successfully")
    void shouldFindAddressesByCustomerIdSuccessfully() {
        AddressDelivery address2 = AddressDelivery.builder().addressDeliveryId(2L).cep("99999999").uf("MG").city("BH").neighborhood("Lourdes").street("Rua B").number("2").customer(customer).build();
        List<AddressDelivery> addresses = Arrays.asList(addressDelivery, address2);

        when(addressDeliveryRepository.findByCustomerCustomerId(customer.getCustomerId())).thenReturn(addresses);

        List<AddressDelivery> result = addressDeliveryService.findByCustomerId(customer.getCustomerId());

        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getCustomer().getCustomerId()).isEqualTo(customer.getCustomerId());
        verify(addressDeliveryRepository, times(1)).findByCustomerCustomerId(customer.getCustomerId());
    }

    @Test
    @DisplayName("Should find address by CEP, number, and customer ID successfully")
    void shouldFindAddressByCepAndNumberAndCustomerIdSuccessfully() {
        when(addressDeliveryRepository.findByCepAndNumberAndCustomerCustomerId(
                addressRequest.cep(), addressRequest.number(), customer.getCustomerId()))
                .thenReturn(Optional.of(addressDelivery));

        AddressDelivery result = addressDeliveryService.findByCepAndNumberAndCustomerId(
                addressRequest.cep(), addressRequest.number(), customer.getCustomerId());

        assertThat(result).isNotNull();
        assertThat(result.getAddressDeliveryId()).isEqualTo(addressDelivery.getAddressDeliveryId());
        verify(addressDeliveryRepository, times(1)).findByCepAndNumberAndCustomerCustomerId(
                addressRequest.cep(), addressRequest.number(), customer.getCustomerId());
    }

    @Test
    @DisplayName("Should throw AddressDeliveryNotFoundException when address by CEP, number, customer ID not found")
    void shouldThrowExceptionWhenAddressByCepNumberCustomerIdNotFound() {
        when(addressDeliveryRepository.findByCepAndNumberAndCustomerCustomerId(
                "nonexistent", "nonexistent", 2L))
                .thenReturn(Optional.empty());

        assertThrows(AddressDeliveryNotFoundException.class, () ->
                addressDeliveryService.findByCepAndNumberAndCustomerId("nonexistent", "nonexistent", 2L));
        verify(addressDeliveryRepository, times(1)).findByCepAndNumberAndCustomerCustomerId(
                "nonexistent", "nonexistent", 2L);
    }
}