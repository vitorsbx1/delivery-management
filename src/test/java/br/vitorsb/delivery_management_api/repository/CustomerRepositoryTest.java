package br.vitorsb.delivery_management_api.repository;

import br.vitorsb.delivery_management_api.domain.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("h2")
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        customerRepository.deleteAll();
    }

    @Test
    @DisplayName("should save a customer successfully")
    void shouldSaveCustomerSuccessfully() {
        Customer customer = Customer.builder()
                .name("vITOR")
                .cpf("123123")
                .build();

        Customer savedCustomer = customerRepository.save(customer);

        assertThat(savedCustomer).isNotNull();
        assertThat(savedCustomer.getCustomerId()).isNotNull();
        assertThat(savedCustomer.getName()).isEqualTo("vITOR");

        assertThat(savedCustomer.getCpf()).isEqualTo("123123");
    }

    @Test
    @DisplayName("should find customer byCPF")
    void shouldFindCustomerByCpf() {
        Customer customer = Customer.builder()
                .name("Vitor")
                .cpf("22222222222")
                .build();
        customerRepository.save(customer);

        Optional<Customer> foundCustomer = customerRepository.findByCpf("22222222222");

        assertThat(foundCustomer).isPresent();
        assertThat(foundCustomer.get().getName()).isEqualTo("Vitor");
    }

    @Test
    @DisplayName("should not find customer by cpf non nexistent")
    void shouldNotFindCustomerByNonExistentCpf() {
        Optional<Customer> foundCustomer = customerRepository.findByCpf("99999999999");
        assertThat(foundCustomer).isEmpty();
    }

    @Test
    @DisplayName("should find customer by ID")
    void shouldFindCustomerById() {
        Customer customer = Customer.builder()
                .name("Vitor1")
                .cpf("33333333333")
                .build();
        customer = customerRepository.save(customer);

        Optional<Customer> foundCustomer = customerRepository.findById(customer.getCustomerId());
        assertThat(foundCustomer).isPresent();
        assertThat(foundCustomer.get().getName()).isEqualTo("Vitor1");
    }

    @Test
    @DisplayName("Should delete by customer id")
    void shouldDeleteCustomerById() {
        Customer customer = Customer.builder()
                .name("VitorDelete")
                .cpf("1234123")
                .build();
        customer = customerRepository.save(customer);

        customerRepository.deleteById(customer.getCustomerId());

        Optional<Customer> deletedCustomer = customerRepository.findById(customer.getCustomerId());
        assertThat(deletedCustomer).isEmpty();
    }
}