package ru.wms.WarehouseManagementService.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "warehouse_t")
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 2, message = "Имя должно содержать не менее 2 символов")
    private String name;

    private String address;

    private int capacity;

    @Column(name = "creation_date", columnDefinition = "DATE")
    private LocalDate creationDate;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @OneToMany(mappedBy = "warehouse", cascade = CascadeType.REMOVE)
    private List<Product> productList;


}
