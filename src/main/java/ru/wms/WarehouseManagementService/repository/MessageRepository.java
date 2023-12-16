package ru.wms.WarehouseManagementService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.wms.WarehouseManagementService.entity.messenger.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message,Long> {
}
