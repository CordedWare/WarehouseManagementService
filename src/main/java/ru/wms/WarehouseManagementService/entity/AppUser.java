package ru.wms.WarehouseManagementService.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import ru.wms.WarehouseManagementService.security.Authority;

import java.util.Set;

@Data
@Entity
@ToString
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @ElementCollection(targetClass = Authority.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_authorities", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Authority> authorities;

}