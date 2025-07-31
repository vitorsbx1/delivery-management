package br.vitorsb.delivery_management_api.repository;

import br.vitorsb.delivery_management_api.domain.AddressDelivery;
import br.vitorsb.delivery_management_api.domain.Customer;
import br.vitorsb.delivery_management_api.domain.Delivery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@ActiveProfiles("h2")
class DeliveryRepositoryTest {

    @Autowired
    private  DeliveryRepository deliveryRepository;
    @Autowired private  CustomerRepository customerRepository;
    @Autowired private  AddressDeliveryRepository addressDeliveryRepository;


    private Customer customer;
    private AddressDelivery addressDelivery;


    @BeforeEach
    void setUp() {

        deliveryRepository.deleteAll();
        addressDeliveryRepository.deleteAll();
        customerRepository.deleteAll();


        customer = Customer.builder()
                .name("Vitor")
                .cpf("7777777777")
                .build();
        customer = customerRepository.save(customer);

        addressDelivery = AddressDelivery.builder()
                .cep("12345678")
                .uf("MG")
                .city("Uberlandia")
                .neighborhood("Centro")
                .street("Rua Luiza Labs")
                .number("123")
                .customer(customer)
                .build();
        addressDelivery = addressDeliveryRepository.save(addressDelivery);
    }

    @Test
    @DisplayName("sHOULD save a delivery successfully")
    void shouldSaveDeliverySuccessfully() {
        Delivery delivery = Delivery.builder()
                .packageQuantity(2)
                .deliveryDeadline(LocalDateTime.now().plusDays(1))
                .customer(customer)
                .addressDelivery(addressDelivery)
                .build();

        Delivery savedDelivery = deliveryRepository.save(delivery);

        assertThat(savedDelivery).isNotNull();
        assertThat(savedDelivery.getDeliveryId()).isNotNull();
        assertThat(savedDelivery.getPackageQuantity()).isEqualTo(delivery.getPackageQuantity());
        assertThat(savedDelivery.getCustomer().getCustomerId()).isEqualTo(customer.getCustomerId());
        assertThat(savedDelivery.getAddressDelivery().getAddressDeliveryId()).isEqualTo(addressDelivery.getAddressDeliveryId());
    }

    @Test
    @DisplayName("Should find delivery by id")
    void shouldFindDeliveryById() {
        Delivery delivery = Delivery.builder()
                .packageQuantity(1)
                .deliveryDeadline(LocalDateTime.now().plusDays(3))
                .customer(customer)
                .addressDelivery(addressDelivery)
                .build();

        delivery = deliveryRepository.save(delivery);

        Optional<Delivery> foundDelivery = deliveryRepository
                .findById(delivery.getDeliveryId());

        assertThat(foundDelivery).isPresent();
        assertThat(foundDelivery.get().getDeliveryId()).isEqualTo(delivery.getDeliveryId());
    }

    @Test
    @DisplayName("Shloud delete delivery by id")
    void shouldDeleteDeliveryById() {
        Delivery delivery = Delivery.builder()
                .packageQuantity(3)
                .deliveryDeadline(LocalDateTime.now().plusDays(7))
                .customer(customer)
                .addressDelivery(addressDelivery)
                .build();
        delivery = deliveryRepository.save(delivery);

        deliveryRepository.deleteById(delivery.getDeliveryId());

        Optional<Delivery> deletedDelivery = deliveryRepository.findById(delivery.getDeliveryId());
        assertThat(deletedDelivery).isEmpty();
    }
}