package net.bean.java.open.messenger.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bean.java.open.messenger.data.dto.InputMessageDTO;
import net.bean.java.open.messenger.data.dto.OutputMessageDTO;
import net.bean.java.open.messenger.data.jpa.model.Message;
import net.bean.java.open.messenger.data.mapper.MessageMapper;
import net.bean.java.open.messenger.data.jpa.model.User;
import net.bean.java.open.messenger.service.MessageService;
import net.bean.java.open.messenger.service.UserService;
import net.bean.java.open.messenger.util.UserRequestUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MessagesResource {
    private final MessageService messageService;
    private final UserService userService;
    private final MessageMapper mapper;

    @GetMapping("/api/messages")
    public ResponseEntity getMessages(HttpServletRequest request, @RequestParam long user1, @RequestParam long user2, @RequestParam Optional<Integer> page, @RequestParam(value = "lowerIdThan", required = false) Optional<Long> lowerIdThan) {
        User userFromToken = userService.getUser(UserRequestUtil.getUserFromHttpServletRequest(request)).get();
        User userFromPath1 = userService.getUser(user1).orElseThrow();
        User userFromPath2 = userService.getUser(user2).orElseThrow();

        if(userFromToken.getId() == userFromPath1.getId() || userFromToken.getId() == userFromPath2.getId()) {
            List<Message> returendMessages;
            if(!lowerIdThan.isPresent() || (lowerIdThan.get() == 0)) {
                returendMessages = messageService.getMessages(userFromPath1.getId(), userFromPath2.getId(), page);
            } else {
                returendMessages = messageService.getMessagesWithLowerIdThan(userFromPath1.getId(), userFromPath2.getId(), page, lowerIdThan.get());
            }
            List<OutputMessageDTO> messages = returendMessages
                    .stream()
                    .map(mapper::mapEntityToDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok().body(messages);
        } else {
            return new ResponseEntity<String>("You are unauthorized to query data belonging to another user.", HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/api/messages/{id}")
    public ResponseEntity getMessageById(HttpServletRequest request, @PathVariable("id") long id) {
        User userFromToken = userService.getUser(UserRequestUtil.getUserFromHttpServletRequest(request)).get();
        Message message = messageService.getMessageById(id);
        if(message == null) {
            return ResponseEntity.noContent().build();
        } else if(message.getRecipient().getId() == userFromToken.getId() || message.getSender().getId() == userFromToken.getId()) {
            return ResponseEntity.ok().body(mapper.mapEntityToDto(message));
        } else {
            return new ResponseEntity<String>("You are unauthorized to query data belonging to another user.", HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping(value = "/api/messages")
    public ResponseEntity<OutputMessageDTO> newMessage(HttpServletRequest request, @RequestBody InputMessageDTO messageDTO) {
        User curentUser = userService.getUser(UserRequestUtil.getUserFromHttpServletRequest(request)).get();
        Message message = messageService.saveMessage(messageDTO, curentUser.getId());
        return ResponseEntity.ok().body(mapper.mapEntityToDto(message));
    }


}
