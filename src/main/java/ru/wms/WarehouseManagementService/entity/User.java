package ru.wms.WarehouseManagementService.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.wms.WarehouseManagementService.security.Authority;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "user_t")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String username; // TODO: пока необходимости в username нет, т.к. логинимся через почту, возможно потом стоит убрать

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private boolean active;

    private String activationCode;

    @ElementCollection(targetClass = Authority.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_authorities_t", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Authority> authorities;
}