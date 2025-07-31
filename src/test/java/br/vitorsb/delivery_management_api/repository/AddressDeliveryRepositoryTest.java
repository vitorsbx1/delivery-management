package br.vitorsb.delivery_management_api.repository;

import br.vitorsb.delivery_management_api.domain.AddressDelivery;
import br.vitorsb.delivery_management_api.domain.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("h2")
class AddressDeliveryRepositoryTest {

    @Autowired
    private AddressDeliveryRepository addressDeliveryRepository;
    @Autowired
    private CustomerRepository customerRepository;

    private Customer testCustomer;
    private Customer anotherCustomer;

    @BeforeEach
    void setUp() {
        addressDeliveryRepository.deleteAll();
        customerRepository.deleteAll();

        testCustomer = Customer.builder()
                .name("Vitor")
                .cpf("00000000001")
                .build();
        testCustomer = customerRepository.save(testCustomer);


        anotherCustomer = Customer.builder()
                .name("Igor")
                .cpf("00000000002")
                .build();
        anotherCustomer = customerRepository.save(anotherCustomer);
    }

    @Test
    @DisplayName("should save an address successfully")
    void shouldSaveAddressSuccessfully() {
        AddressDelivery address = AddressDelivery.builder()
                .cep("87654321")
                .uf("RJ")
                .city("Rio de Janeiro")
                .neighborhood("Copacabana")
                .street("Rua Teste Endereco")
                .number("456")
                .customer(testCustomer)
                .build();

        AddressDelivery savedAddress = addressDeliveryRepository.save(address);

        assertThat(savedAddress).isNotNull();
        assertThat(savedAddress.getAddressDeliveryId()).isNotNull();
        assertThat(savedAddress.getCep()).isEqualTo("87654321");
        assertThat(savedAddress.getCustomer().getCustomerId()).isEqualTo(testCustomer.getCustomerId());
    }

    @Test
    @DisplayName("Should find addresses by customer ID")
    void shouldFindAddressesByCustomerId() {
        AddressDelivery address1 = AddressDelivery.builder()
                .cep("11111111")
                .uf("MG").city("BH").neighborhood("Lourdes").street("Rua A").number("1").customer(testCustomer).build();
        AddressDelivery address2 = AddressDelivery.builder()
                .cep("22222222")
                .uf("MG").city("BH").neighborhood("Savassi").street("Rua B").number("2").customer(testCustomer).build();
        AddressDelivery address3 = AddressDelivery.builder()
                .cep("33333333")
                .uf("SP").city("SP").neighborhood("Paulista").street("Rua C").number("3").customer(anotherCustomer).build();

        addressDeliveryRepository.saveAll(List.of(address1, address2, address3));

        List<AddressDelivery> customerAddresses = addressDeliveryRepository.findByCustomerCustomerId(testCustomer.getCustomerId());

        assertThat(customerAddresses).isNotNull();
        assertThat(customerAddresses).hasSize(2);
        assertThat(customerAddresses.stream().allMatch(a -> a.getCustomer().getCustomerId().equals(testCustomer.getCustomerId()))).isTrue();
    }

    @Test
    @DisplayName("Should find address by CEP, number and customer ID")
    void shouldFindAddressByCepNumberAndCustomerId() {
        AddressDelivery address = AddressDelivery.builder()
                .cep("98765432")
                .uf("RS").city("Porto Alegre").neighborhood("Moinhos").street("Av. Ind").number("789").customer(testCustomer).build();
        addressDeliveryRepository.save(address);

        Optional<AddressDelivery> foundAddress = addressDeliveryRepository.findByCepAndNumberAndCustomerCustomerId(
                "98765432", "789", testCustomer.getCustomerId());

        assertThat(foundAddress).isPresent();
        assertThat(foundAddress.get().getStreet()).isEqualTo("Av. Ind");
        assertThat(foundAddress.get().getCustomer().getCustomerId()).isEqualTo(testCustomer.getCustomerId());
    }

    @Test
    @DisplayName("Should not find address for wrong customer ID")
    void shouldNotFindAddressForWrongCustomerId() {
        AddressDelivery address = AddressDelivery.builder()
                .cep("98765432")
                .uf("RS").city("Porto Alegre").neighborhood("Moinhos").street("Av. Ind").number("789").customer(testCustomer).build();
        addressDeliveryRepository.save(address);

        Optional<AddressDelivery> foundAddress = addressDeliveryRepository.findByCepAndNumberAndCustomerCustomerId(
                "98765432", "789", anotherCustomer.getCustomerId());

        assertThat(foundAddress).isEmpty();
    }
}