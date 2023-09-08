package ru.wms.WarehouseManagementService.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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
}
