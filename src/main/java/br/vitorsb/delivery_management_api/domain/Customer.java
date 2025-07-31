package br.vitorsb.delivery_management_api.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = "addressDeliveries")
@Builder
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "cpf", nullable = false)
    private String cpf;

    @Builder.Default
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AddressDelivery> addressDeliveries = new ArrayList<>();
}
