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

    @OneToOne
    private User initiator;

    @OneToOne
    private User partner;

    @OneToMany(mappedBy = "chat",cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private List<Message> messages;

}
