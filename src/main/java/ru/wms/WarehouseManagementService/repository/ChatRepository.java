package ru.wms.WarehouseManagementService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.wms.WarehouseManagementService.entity.User;
import ru.wms.WarehouseManagementService.entity.messenger.Chat;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat,Long> {

    Optional<Chat> findByInitiatorAndPartnerId(User initiator, Long partnerId);
    Optional<Chat> findByInitiatorIdAndPartnerId(Long initiatorId, Long partnerId);

    List<Chat> findAllByInitiatorOrPartner(User initiator, User partner);

}
