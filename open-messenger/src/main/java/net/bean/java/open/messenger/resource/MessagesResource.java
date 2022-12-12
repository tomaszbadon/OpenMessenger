package net.bean.java.open.messenger.resource;

import lombok.RequiredArgsConstructor;
import net.bean.java.open.messenger.entity.InputMessagePayload;
import net.bean.java.open.messenger.entity.MessagePayload;
import net.bean.java.open.messenger.entity.OutputMessagePayload;
import net.bean.java.open.messenger.model.jpa.Message;
import net.bean.java.open.messenger.model.jpa.User;
import net.bean.java.open.messenger.service.CurrentUserService;
import net.bean.java.open.messenger.service.MessageService;
import net.bean.java.open.messenger.service.NotificationSerivce;
import net.bean.java.open.messenger.util.Pause;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MessagesResource {

    private final MessageService messageService;

    private final NotificationSerivce notificationSerivce;

    private final CurrentUserService currentUserService;

    @GetMapping("/api/messages")
    public ResponseEntity<MessagePayload> getMessages(HttpServletRequest request, @RequestParam long user1, @RequestParam long user2, @RequestParam Optional<Integer> page) {
        //TODO validation
        List<Message> returnedMessage = messageService.getMessages(user1, user2, page);
        List<OutputMessagePayload> messages = returnedMessage
                .stream()
                .map(OutputMessagePayload::new)
                .collect(Collectors.toList());
        Pause._for(1000);
        return ResponseEntity.ok().body(new MessagePayload(messages, page.orElseGet(() -> 0)));
    }

    @GetMapping("/api/messages/latest")
    public ResponseEntity<MessagePayload> getLatestMessages(HttpServletRequest request, @RequestParam long user1, @RequestParam long user2) {
        Optional<Integer> page = Optional.of(messageService.getLastPage(user1, user2));
        List<OutputMessagePayload> messages = messageService.getMessages(user1, user2, page).stream()
                .map(OutputMessagePayload::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new MessagePayload(messages, page.get()));
    }

    @GetMapping("/api/messages/{id}")
    public ResponseEntity getMessageById(HttpServletRequest request, @PathVariable("id") long id) {

        User userFromToken = currentUserService.getUserFromTokenOrElseThrowException(request);

        Message message = messageService.getMessageById(id);
        if (message == null) {
            return ResponseEntity.noContent().build();
        } else if (message.getRecipient().getId() == userFromToken.getId() || message.getSender().getId() == userFromToken.getId()) {
            return ResponseEntity.ok().body(new OutputMessagePayload(message));
        } else {
            return new ResponseEntity<String>("You are unauthorized to query data belonging to another user.", HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping(value = "/api/messages")
    public ResponseEntity<OutputMessagePayload> newMessage(HttpServletRequest request, @RequestBody InputMessagePayload messageDTO) {
        User currentUser = currentUserService.getUserFromTokenOrElseThrowException(request);
        Message message = messageService.saveMessage(messageDTO, currentUser.getId());
        notificationSerivce.notifyUser(message);
        return ResponseEntity.ok().body(new OutputMessagePayload(message));
        //TODO: Created instead of ok();
    }

}