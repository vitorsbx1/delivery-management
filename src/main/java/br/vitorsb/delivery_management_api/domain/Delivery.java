package br.vitorsb.delivery_management_api.domain;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

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
    private Long deliveryId;

    @Column(name = "package_quantity", nullable = false)
    private Integer packageQuantity;

    @Column(name = "delivery_deadline", nullable = false)
    private LocalDate deliveryDeadline;

    @ManyToOne
    @JoinColumn(referencedColumnName = "customer_id", name = "customer_id", nullable = false)
    private Customer customer;
}

