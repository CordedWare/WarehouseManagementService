package ru.wms.WarehouseManagementService.entity;

import jakarta.persistence.*;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "customer_t")
public class Customer extends User {


    @Column
    private Integer telephone;

    @Column
    private String nameOrg;

    @Column
    private String addressOrg;

    @Column
    private String contactInfoOrg;

    @Override
    public String toString() {
        return "Customer{" +
                "pass=" + getPassword() +
                ", ema='" + getEmail() + '\'' +
                ", addressOrg='" + addressOrg + '\'' +
                ", contactInfoOrg='" + contactInfoOrg + '\'' +
                '}';
    }
}