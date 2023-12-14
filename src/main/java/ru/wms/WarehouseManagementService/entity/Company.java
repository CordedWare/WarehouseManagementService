package ru.wms.WarehouseManagementService.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "company_t")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;
    private String address;


    @OneToMany(mappedBy = "company")
    private Set<Warehouse> warehouses;

    @OneToMany(mappedBy = "company")
    private Set<Product> products;

    @OneToMany(mappedBy = "company")
    private Set<Order> orders;

    @OneToMany(mappedBy = "company")
    private Set<Employee> employees;

    @OneToMany(mappedBy = "company")
    private Set<User> employess;

}