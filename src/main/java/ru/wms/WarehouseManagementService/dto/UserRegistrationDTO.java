package ru.wms.WarehouseManagementService.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserRegistrationDTO {

    private String username;

    private String password;

    private String email;

}
