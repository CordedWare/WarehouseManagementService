package ru.wms.WarehouseManagementService.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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
    @Column(name = "creation_date")
    private LocalDate creationDate;
}
