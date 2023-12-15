package ru.wms.WarehouseManagementService.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "client_t")
public class Client extends User {

    @Column
    private Long telephone;

    @Column
    private String contactInfoOrg;



}