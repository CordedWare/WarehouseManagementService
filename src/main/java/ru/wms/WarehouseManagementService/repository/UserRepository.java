package ru.wms.WarehouseManagementService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.wms.WarehouseManagementService.entity.User;

import java.io.Serializable;
import java.util.Optional;

@Repository
public interface UserRepository<T extends User,E extends Serializable> extends JpaRepository<T, E> {

    Optional<T> findByEmail(String email);

    Optional<T> findByActivationCode(String code);

    Optional<T> findById(E id);
}
