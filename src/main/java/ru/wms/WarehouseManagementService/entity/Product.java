package ru.wms.WarehouseManagementService.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min = 2, message = "Имя должно содержать не менее 2 символов")
    private String name;
    private String description;
    private String category;
    private BigDecimal price;
    private int quantity;
    @Column(name = "creation_date", columnDefinition = "DATE")
    private LocalDate creationDate;
}
