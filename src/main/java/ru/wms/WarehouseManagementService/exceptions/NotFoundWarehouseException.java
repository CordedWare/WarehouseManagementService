package ru.wms.WarehouseManagementService.exceptions;

public class NotFoundWarehouseException extends WarehouseException{
    public NotFoundWarehouseException(String message) {
        super(message);
    }
}
