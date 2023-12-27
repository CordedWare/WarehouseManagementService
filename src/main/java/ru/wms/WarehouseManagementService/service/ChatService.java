package ru.wms.WarehouseManagementService.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.wms.WarehouseManagementService.dto.MessageForm;
import ru.wms.WarehouseManagementService.entity.User;
import ru.wms.WarehouseManagementService.entity.messenger.Chat;
import ru.wms.WarehouseManagementService.entity.messenger.Message;
import ru.wms.WarehouseManagementService.repository.ChatRepository;
import ru.wms.WarehouseManagementService.repository.MessageRepository;
import ru.wms.WarehouseManagementService.repository.UserRepository;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;

    private final UserRepository<User, Long> userRepository;

    private final MessageRepository messageRepository;

    public Chat findChat(User user, Long partnerId) {

        var chat = chatRepository.findByInitiatorAndPartnerId(user, partnerId);

        if(chat.isEmpty())
            chat = chatRepository.findByInitiatorIdAndPartnerId(partnerId, user.getId());

        if (chat.isEmpty()) {
            var newChat = new Chat();
            newChat.setInitiator(Collections.singletonList(user));
            newChat.setPartner(Collections.singletonList(userRepository.findById(partnerId).get()));

            chatRepository.save(newChat);

            return newChat;
        }

        return chat.get();
    }

    public Message addMessageChat(User sender, MessageForm messageForm) {
        var chat = chatRepository.findById(messageForm.getChatId());
        var message = new Message();
        message.setText(messageForm.getText());
        message.setSender(sender);
        message.setChat(chat.get());

       return messageRepository.save(message);
    }

    public List<Chat> getChats(User user) {
        return chatRepository.findAllByInitiatorOrPartner(user,user);
    }

    public Chat getById(Long id) {
        return chatRepository.findById(id).get();
    }
}
