package ru.wms.WarehouseManagementService.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity(name = "app_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "creation_date")
    private LocalDate creationDate;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private int quantity;
    @Column(name = "total_cost")
    private BigDecimal totalCost;
    private boolean delivery;
}
