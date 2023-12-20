package ru.wms.WarehouseManagementService.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "product_t")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 2, message = "Имя должно содержать не менее 2 символов")
    private String name;

    private String description;

    private String category;

    private BigDecimal price;

    private Integer quantity;

    @Column(name = "creation_date", columnDefinition = "DATE")
    private LocalDate creationDate;

    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    @ManyToMany(mappedBy = "products")
    private Set<Order> orderSet;

    @Override
    public String toString() {
        String warehouseName = (warehouse != null) ? warehouse.getName() : "1";

        return "Product{"       +
                "id= "          + id +
                ", name='"      + name + '\'' +
                ", category='"  + category + '\'' +
                ", price="      + price +
                ", quantity="   + quantity +
                ", warehouse="  + warehouse.getName() +
                '}';
    }
}
