package ru.wms.WarehouseManagementService.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "customer_t")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private String fullName;

    @Column
    private Integer telephone;

    @Column
    private String nameOrg;

    @Column
    private String addressOrg;

    @Column
    private String contactInfoOrg;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
