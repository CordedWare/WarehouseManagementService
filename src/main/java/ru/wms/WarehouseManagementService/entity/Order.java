package ru.wms.WarehouseManagementService.entity;

import jakarta.persistence.*;
import lombok.*;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity(name = "app_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private int quantity;
    @Column(name = "total_cost")
    private BigDecimal totalCost;
    private boolean delivery;
    @Column(name = "creation_date")
    private LocalDate creationDate;

    @OneToMany
    private List<Product> products;

}
