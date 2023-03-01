package net.bean.java.open.messenger.rest.resource;

import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import net.bean.java.open.messenger.rest.model.InitialMessagePagesPayload;
import net.bean.java.open.messenger.rest.model.OutputMessagesPayload;
import net.bean.java.open.messenger.service.MessageServiceV2;
import net.bean.java.open.messenger.util.HttpServletRequestUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class MessagesResource {

    private final MessageServiceV2 messageServiceV2;

    @GetMapping("/api/messages/users/{userId}")
    public ResponseEntity<OutputMessagesPayload> getMessages(HttpServletRequest request, @PathVariable("userId") long userId, Optional<Integer> page) {
        Try<String> token = HttpServletRequestUtil.getToken(request);
        return ResponseEntity.ok().body(messageServiceV2.readMessages(token, userId, page));
    }

    @GetMapping("/api/messages/users/unread/{userId}")
    public ResponseEntity<InitialMessagePagesPayload> getInitialPages(HttpServletRequest request, @PathVariable("userId") long userId) {
        Try<String> token = HttpServletRequestUtil.getToken(request);
        return ResponseEntity.ok().body(messageServiceV2.getLatestPagesToLoad(token, userId));
    }


}