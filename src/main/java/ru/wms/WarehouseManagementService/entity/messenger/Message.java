package ru.wms.WarehouseManagementService.entity.messenger;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import ru.wms.WarehouseManagementService.entity.User;

import java.time.LocalDateTime;

@Data
@Getter
@Entity(name = "message_t")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "chat_id", nullable = false)
    private Chat chat;

    @Column(nullable = false)
    private String text;

    @ManyToOne
    private User sender;

//    @Column(columnDefinition = "TIMESTAMP")
//    private LocalDateTime createTime;
}
