package ru.wms.WarehouseManagementService.dto;

import lombok.Data;

@Data
public class RegistrationForm {
    private String firstname;
    private String lastname;
    private String patronymic;
    private String password;
    private String email;
    private Long telephone;
}
