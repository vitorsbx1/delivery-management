package br.vitorsb.delivery_management_api.repository;

import br.vitorsb.delivery_management_api.domain.AddressDelivery;
import br.vitorsb.delivery_management_api.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressDeliveryRepository extends JpaRepository<AddressDelivery, Long> {

}
