package net.bean.java.open.messenger.web.soceket.controller;

import net.bean.java.open.messenger.data.dto.InputMessageDTO;
import net.bean.java.open.messenger.data.dto.OutputMessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class GreetingHeroController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public OutputMessageDTO greet(@Header("simpSessionId") String sessionId, Principal user, @Payload InputMessageDTO message) {
        OutputMessageDTO out = new OutputMessageDTO();
        out.setMessage(message.getMessage());
        out.setAcknowledged(false);
        //simpMessagingTemplate.convertAndSendToUser(user.getName(), "/queue/new", out);
        return out;
    }

}
