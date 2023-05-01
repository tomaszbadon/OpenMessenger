package net.bean.java.open.messenger.rest.resource;

import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import net.bean.java.open.messenger.rest.model.InitialMessagePagesPayload;
import net.bean.java.open.messenger.rest.model.InputMessagePayload;
import net.bean.java.open.messenger.rest.model.OutputMessagePayload;
import net.bean.java.open.messenger.rest.model.OutputMessagesPayload;
import net.bean.java.open.messenger.service.MessageService;
import net.bean.java.open.messenger.util.HttpServletRequestUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class MessagesResource {

    private final MessageService messageService;

    @GetMapping("/api/users/{userId}/messages/{page}")
    public ResponseEntity<OutputMessagesPayload> getMessages(HttpServletRequest request, @PathVariable("userId") String userId, @PathVariable("page") Optional<Integer> page) {
        Try<String> token = HttpServletRequestUtil.getToken(request);
        return ResponseEntity.ok().body(messageService.readMessages(token, userId, page));
    }

    @GetMapping("/api/users/{userId}/messages/latest")
    public ResponseEntity<InitialMessagePagesPayload> getInitialPages(HttpServletRequest request, @PathVariable("userId") String userId) {
        Try<String> token = HttpServletRequestUtil.getToken(request);
        return ResponseEntity.ok().body(messageService.getLatestPagesToLoad(token, userId));
    }

    @GetMapping("/api/messages/{messageId}")
    public ResponseEntity<OutputMessagePayload> getMessage(HttpServletRequest request, @PathVariable("messageId") String messageId) {
        Try<String> token = HttpServletRequestUtil.getToken(request);
        return ResponseEntity.ok(messageService.readMessage(token, messageId));
    }

    @PostMapping("/api/messages")
    public ResponseEntity<OutputMessagePayload> sendMessage(HttpServletRequest request, @RequestBody InputMessagePayload inputMessagePayload) {
        Try<String> token = HttpServletRequestUtil.getToken(request);
        OutputMessagePayload outputMessagesPayload = messageService.handleNewMessage(inputMessagePayload, token);
        return ResponseEntity.created(null).body(outputMessagesPayload);
    }

}