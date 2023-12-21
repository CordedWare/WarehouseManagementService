package ru.wms.WarehouseManagementService.entity.messenger;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.wms.WarehouseManagementService.entity.User;

import java.util.List;

@NoArgsConstructor
@Data
@Entity(name = "chat_t")
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToMany
    private List<User> initiator;

    @ManyToMany
    private List<User> partner;

    @OneToMany(mappedBy = "chat",cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private List<Message> messages;

}
