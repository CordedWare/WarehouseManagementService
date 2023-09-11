package ru.wms.WarehouseManagementService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.wms.WarehouseManagementService.entity.AppUser;

@Repository
public interface UserRepository extends JpaRepository<AppUser,Long> {
    @Query("SELECT u FROM AppUser u WHERE u.username = :username")
    AppUser findByUsername(@Param("username") String username);

    AppUser findByActivationCode(String code);
}
