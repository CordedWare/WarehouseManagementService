package ru.wms.WarehouseManagementService.dto;

import lombok.Data;

@Data
public class MessageForm {
    private Long chatId;
    private String text;
}
