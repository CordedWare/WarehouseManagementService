package ru.wms.WarehouseManagementService.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "product_t")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @ManyToOne
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    @Size(min = 2, message = "Имя должно содержать не менее 2 символов")
    private String name;

    private String description;

    private String category;

    private BigDecimal price;

    private int quantity;

    @Column(name = "creation_date", columnDefinition = "DATE")
    private LocalDate creationDate;
}
