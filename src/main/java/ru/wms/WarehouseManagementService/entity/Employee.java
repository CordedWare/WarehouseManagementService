package ru.wms.WarehouseManagementService.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "employee_t")
public class Employee extends User {

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    public Customer getCustomer() {

        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
