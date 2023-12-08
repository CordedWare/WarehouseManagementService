package ru.wms.WarehouseManagementService.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class OrderMoveDTO {

    private Long orderId;

    private Long warehouseId;

}
