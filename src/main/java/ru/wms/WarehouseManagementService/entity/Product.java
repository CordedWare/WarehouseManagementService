package ru.wms.WarehouseManagementService.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "product_t")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;


    @ManyToOne
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    @ManyToMany(mappedBy = "products")
    private Set<Order> orderSet;

    @Size(min = 2, message = "Имя должно содержать не менее 2 символов")
    private String name;

    private String description;

    private String category;

    private BigDecimal price;

    private int quantity;

    @Column(name = "creation_date", columnDefinition = "DATE")
    private LocalDate creationDate;

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", warehouse=" + warehouse.getName() +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", creationDate=" + creationDate +
                '}';
    }
}
