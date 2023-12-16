package ru.wms.WarehouseManagementService.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "client_t")
public class Client extends User {
}