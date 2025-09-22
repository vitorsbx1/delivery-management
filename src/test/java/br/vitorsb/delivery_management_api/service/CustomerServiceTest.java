package br.vitorsb.delivery_management_api.service;

import br.vitorsb.delivery_management_api.config.exception.CustomerNotFoundException;
import br.vitorsb.delivery_management_api.domain.Customer;
import br.vitorsb.delivery_management_api.domain.dto.request.CustomerRequest;
import br.vitorsb.delivery_management_api.domain.dto.response.CustomerResponse;
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
    private CustomerResponse customerResponse;

    @BeforeEach
    void setUp() {
        customer = Customer.builder()
                .customerId(1L)
                .name("Vitor")
                .cpf("123.124.124-00")
                .build();

        customerRequest = new CustomerRequest("Vitor", "123.124.124-00");
        customerResponse = new CustomerResponse(customer.getName(), customer.getCpf());
    }

    @Test
    @DisplayName("Should find or create customer when exists")
    void shouldFindOrCreateCustomerWhenExists() {
        when(customerRepository.findByCpf(customerRequest.cpf())).thenReturn(Optional.of(customer));

        Customer result = customerService.findOrCreate(customerRequest);

        assertThat(result).isNotNull();
        assertThat(result.getCustomerId()).isEqualTo(customer.getCustomerId());
        assertThat(result.getName()).isEqualTo(customer.getName());
        assertThat(result.getCpf()).isEqualTo(customer.getCpf());

        verify(customerRepository).findByCpf(customerRequest.cpf());
        verify(customerRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should find or create customer when not exists")
    void shouldFindOrCreateCustomerWhenNotExists() {
        when(customerRepository.findByCpf(customerRequest.cpf())).thenReturn(Optional.empty());
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        Customer result = customerService.findOrCreate(customerRequest);

        assertThat(result).isNotNull();
        assertThat(result.getCustomerId()).isEqualTo(customer.getCustomerId());
        assertThat(result.getName()).isEqualTo(customer.getName());
        assertThat(result.getCpf()).isEqualTo(customer.getCpf());

        verify(customerRepository).findByCpf(customerRequest.cpf());
        verify(customerRepository).save(any(Customer.class));
    }

    @Test
    @DisplayName("Should find customer by ID successfully")
    void shouldFindCustomerByIdSuccessfully() {
        when(customerRepository.findById(customer.getCustomerId())).thenReturn(Optional.of(customer));

        Customer result = customerService.findById(customer.getCustomerId());

        assertThat(result).isNotNull();
        assertThat(result.getCustomerId()).isEqualTo(customer.getCustomerId());
        assertThat(result.getName()).isEqualTo(customer.getName());
        assertThat(result.getCpf()).isEqualTo(customer.getCpf());

        verify(customerRepository).findById(customer.getCustomerId());
    }

    @Test
    @DisplayName("Should throw CustomerNotFoundException when customer not found by ID")
    void shouldThrowExceptionWhenCustomerNotFound() {
        Long nonExistentId = 999L;
        when(customerRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        CustomerNotFoundException exception = assertThrows(CustomerNotFoundException.class,
                () -> customerService.findById(nonExistentId));

        assertThat(exception.getMessage()).contains(String.valueOf(nonExistentId));
        verify(customerRepository).findById(nonExistentId);
    }


}
