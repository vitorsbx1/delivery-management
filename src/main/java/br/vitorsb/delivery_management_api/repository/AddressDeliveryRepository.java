package br.vitorsb.delivery_management_api.repository;

import br.vitorsb.delivery_management_api.domain.AddressDelivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AddressDeliveryRepository extends JpaRepository<AddressDelivery, Long> {

    @Query("SELECT a FROM AddressDelivery a WHERE a.customer.customerId = ?1")
    List<AddressDelivery> findByCustomerCustomerId(Long id);

    @Query("SELECT a FROM AddressDelivery a WHERE a.cep = ?1 AND a.number = ?2 AND a.customer.customerId = ?3")
    Optional<AddressDelivery> findByCepAndNumberAndCustomerCustomerId(String cep, String number, Long customerId);

}
