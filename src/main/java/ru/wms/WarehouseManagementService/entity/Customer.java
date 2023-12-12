package ru.wms.WarehouseManagementService.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "customer_t")
public class Customer extends User {

    @Column
    private Long telephone;

    @Column
    private String nameOrg;

    @Column
    private String addressOrg;

    @Column
    private String contactInfoOrg;

    @Override
    public String toString() {

        return "Customer { "          +
                " pass ="             + getPassword() +
                ", email ='"          + getEmail() + '\'' +
                ", addressOrg ='"     + addressOrg + '\'' +
                ", contactInfoOrg ='" + contactInfoOrg + '\'' +
                '}';
    }

}
