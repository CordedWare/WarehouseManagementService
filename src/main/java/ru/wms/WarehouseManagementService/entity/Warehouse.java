package ru.wms.WarehouseManagementService.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Data
@Entity
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 2, message = "Имя должно содержать не менее 2 символов")
    private String name;

    private String address;

    private int capacity;

    private String contactInformation;

    @Column(name = "creation_date", columnDefinition = "DATE")
    private LocalDate creationDate;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
