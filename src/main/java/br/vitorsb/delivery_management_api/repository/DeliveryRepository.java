package br.vitorsb.delivery_management_api.repository;

import br.vitorsb.delivery_management_api.domain.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
}
