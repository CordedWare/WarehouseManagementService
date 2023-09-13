package ru.wms.WarehouseManagementService.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import ru.wms.WarehouseManagementService.security.Authority;

import java.util.Set;

@Data
@Entity(name = "app_user")
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private boolean active;

    private String activationCode;

    @ElementCollection(targetClass = Authority.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_authorities", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Authority> authorities;

}