package br.vitorsb.delivery_management_api.repository;

import br.vitorsb.delivery_management_api.domain.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

    // Custom query methods can be defined here if needed
    // For example, to find deliveries by status or date range
}
