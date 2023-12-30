package ru.wms.WarehouseManagementService.entity;

import jakarta.persistence.*;
import lombok.*;


@Data
@AllArgsConstructor
@Entity(name = "client_t")
public class Client extends User {
}