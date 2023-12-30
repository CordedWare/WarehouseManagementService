package ru.wms.WarehouseManagementService.entity;

import jakarta.persistence.*;
import lombok.*;




@Data
@AllArgsConstructor
@Entity
@Table(name = "employee_t")
public class Employee extends User {


}
