package br.vitorsb.delivery_management_api.service;

import br.vitorsb.delivery_management_api.config.exception.CustomerNotFoundException;
import br.vitorsb.delivery_management_api.domain.Customer;
import br.vitorsb.delivery_management_api.domain.dto.request.CustomerRequest;
import br.vitorsb.delivery_management_api.domain.mapper.CustomerMapper;
import br.vitorsb.delivery_management_api.repository.CustomerRepository;
import br.vitorsb.delivery_management_api.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private Customer customer;
    private CustomerRequest customerRequest;

    @BeforeEach
    void setUp() {
        customer = Customer.builder()
                .customerId(1L)
                .name("Vitor")
                .cpf("123124124")
                .build();
        customerRequest = new CustomerRequest("Vitor", "123124124");
    }

    @Test
    @DisplayName("should find or create customer")
    void shouldFindOrCreateCustomerWhenExists() {
        when(customerRepository.findByCpf(customerRequest.cpf())).thenReturn(Optional.of(customer));

        Customer result = customerService.findOrCreate(customerRequest);

        assertThat(result).isNotNull();
        assertThat(result.getCustomerId()).isEqualTo(customer.getCustomerId());
        verify(customerRepository, times(1)).findByCpf(customerRequest.cpf());
        verify(customerRepository, never()).save(any(Customer.class));
        verify(customerMapper, never()).toEntity(any(CustomerRequest.class));
    }

    @Test
    @DisplayName("should find or create customer when not exists")
    void shouldFindOrCreateCustomerWhenNotExists() {
        when(customerRepository.findByCpf(customerRequest.cpf())).thenReturn(Optional.empty());
        when(customerMapper.toEntity(customerRequest)).thenReturn(customer);

        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        Customer result = customerService.findOrCreate(customerRequest);

        assertThat(result).isNotNull();

        assertThat(result.getCustomerId()).isEqualTo(customer.getCustomerId());
        verify(customerRepository, times(1)).findByCpf(customerRequest.cpf());
        verify(customerMapper, times(1)).toEntity(customerRequest);
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    @DisplayName("should find customer by ID success")
    void shouldFindCustomerByIdSuccessfully() {
        when(customerRepository.findById(customer.getCustomerId())).thenReturn(Optional.of(customer));

        Customer result = customerService.findById(customer.getCustomerId());

        assertThat(result).isNotNull();
        assertThat(result.getCustomerId()).isEqualTo(customer.getCustomerId());
        verify(customerRepository, times(1)).findById(customer.getCustomerId());
    }

    @Test
    @DisplayName("should throw CustomerNotFoundException when customer by ID not found")
    void shouldThrowExceptionWhenCustomerByIdNotFound() {
        when(customerRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> customerService.findById(2L));
        verify(customerRepository, times(1)).findById(2L);
    }
}
