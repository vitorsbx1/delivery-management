package br.vitorsb.delivery_management_api.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Builder
@Table(name = "address_delivery")
public class AddressDelivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "address_id")
    private Long addressDeliveryId;

    @Column(name = "cep", nullable = false)
    private String cep;

    @Column(name = "uf", nullable = false)
    private String uf;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "neighborhood", nullable = false)
    private String neighborhood;

    @Column(name = "street", nullable = false)
    private String street;

    @Column(name = "number", nullable = false)
    private String number;

    @Column(name = "complement")
    private String complement;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
