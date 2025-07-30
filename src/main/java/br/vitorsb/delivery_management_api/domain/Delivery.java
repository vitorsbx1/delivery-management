package br.vitorsb.delivery_management_api.domain;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Builder
@Table(name = "delivery")
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "delivery_id")
    private Long deliveryId;

    @Column(name = "package_quantity", nullable = false)
    private Integer packageQuantity;

    @Column(name = "delivery_deadline", nullable = false)
    private LocalDateTime deliveryDeadline;

    @ManyToOne
    @JoinColumn(referencedColumnName = "customer_id", name = "customer_id", nullable = false)
    private Customer customer;

    @OneToOne
    @JoinColumn(referencedColumnName = "address_id", name = "address_id", nullable = false)
    private AddressDelivery addressDelivery;
}

