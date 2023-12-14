package ru.wms.WarehouseManagementService.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import ru.wms.WarehouseManagementService.security.Authority;

import java.util.Set;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_t")
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = true)
    private String firstname;

    @Column(nullable = true)
    private String lastname;

    @Column(nullable = true)
    private String patronymic;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false,unique = true)
    private String email;

    @Column(nullable = false)
    private boolean active;

    private String activationCode;

    @ElementCollection(targetClass = Authority.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_authorities_t", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Authority> authorities;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private Company company;
}