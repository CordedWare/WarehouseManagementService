package ru.wms.WarehouseManagementService.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.wms.WarehouseManagementService.security.Authority;

import java.util.Set;
@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_t")
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String firstname;
    @Column(nullable = false)
    private String lastname;
    @Column(nullable = true)
    private String patronymic;


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