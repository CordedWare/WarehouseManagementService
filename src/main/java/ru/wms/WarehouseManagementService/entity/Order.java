package ru.wms.WarehouseManagementService.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "order_t")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;

    private boolean delivery;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "total_cost")
    private BigDecimal totalCost;

    @Column(name = "creation_date", columnDefinition = "DATE")
    @CreationTimestamp
    private LocalDateTime creationDate;

    @NotNull
    @Column
    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    @ManyToMany
    private List<Product> products;

}
