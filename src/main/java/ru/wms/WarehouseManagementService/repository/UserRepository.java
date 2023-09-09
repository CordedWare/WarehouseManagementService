package ru.wms.WarehouseManagementService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.wms.WarehouseManagementService.entity.AppUser;

@Repository
public interface UserRepository extends JpaRepository<AppUser,Long> {
    AppUser findByUsername(String email);

}
