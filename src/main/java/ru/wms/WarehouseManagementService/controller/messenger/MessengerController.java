package ru.wms.WarehouseManagementService.controller.messenger;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.wms.WarehouseManagementService.dto.MessageForm;
import ru.wms.WarehouseManagementService.security.UserPrincipal;
import ru.wms.WarehouseManagementService.service.ChatService;

@Controller
@RequestMapping("/messenger")
public class MessengerController {


    private final ChatService chatService;

    @Autowired
    public MessengerController(ChatService chatService) {
        this.chatService = chatService;
    }


    @GetMapping
    public String messenger(@AuthenticationPrincipal UserPrincipal userPrincipal, Model model){
        var chats = chatService.getChats(userPrincipal.getUser());
        model.addAttribute("chats",chats);
        return "messenger/messenger";
    }

    @GetMapping("/chat/{id}")
    public String chat(@PathVariable Long id,
            @AuthenticationPrincipal UserPrincipal userPrincipal){
        var chat = chatService.getById(id);
        var user = userPrincipal.getUser();
        if(chat.getInitiator().getId() == user.getId()) {
            return "redirect:/messenger/chat?partner=" + chat.getPartner().getId();
        }
        return "redirect:/messenger/chat?partner="+chat.getInitiator().getId();
    }


    @GetMapping("/chat")
    public String chat(@RequestParam(name = "partner", required = true) Long partner,
                       Model model,
                       @AuthenticationPrincipal UserPrincipal userPrincipal){

        var chat = chatService.findChat(userPrincipal.getUser(),partner);
        var messages = chat.getMessages();

        model.addAttribute("chat",chat);
        model.addAttribute("messages",messages);
        model.addAttribute("messageForm",new MessageForm());

        return "messenger/chat";
    }

    @PostMapping("/chat/send")
    public String send(MessageForm messageForm,
                       @AuthenticationPrincipal UserPrincipal userPrincipal,
                       HttpServletRequest request){

        chatService.addMessageChat(userPrincipal.getUser(),messageForm);

        var referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }
}
