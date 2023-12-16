package ru.wms.WarehouseManagementService.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "employee_t")
public class Employee extends User {
}
