package net.bean.java.open.messenger.rest.resource;

import lombok.RequiredArgsConstructor;
import net.bean.java.open.messenger.rest.model.InitialMessagePagesPayload;
import net.bean.java.open.messenger.service.CurrentUserService;
import net.bean.java.open.messenger.service.MessageServiceV2;
import net.bean.java.open.messenger.util.HttpServletRequestUtil;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class MessagesResource {

    private final MessageServiceV2 messageServiceV2;

    private final CurrentUserService currentUserService;


    @GetMapping("/api/messages/{userId}")
    public ResponseEntity<InitialMessagePagesPayload> getInitialPages(HttpServletRequest request, @PathVariable("userId") long userId) {
        String token = HttpServletRequestUtil.getToken(request);
        return ResponseEntity.ok().body(messageServiceV2.getLatestPagesToLoad(token, userId));
    }


}