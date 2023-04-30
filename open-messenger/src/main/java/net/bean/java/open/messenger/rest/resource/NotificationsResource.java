package net.bean.java.open.messenger.rest.resource;

import lombok.RequiredArgsConstructor;
import net.bean.java.open.messenger.rest.model.MessageAcknownledge;
import net.bean.java.open.messenger.rest.model.Notification;
import net.bean.java.open.messenger.service.NotificationService;
import net.bean.java.open.messenger.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class NotificationsResource {

    private final NotificationService notificationsService;
    private final UserService userService;

    @GetMapping("/api/users/{userId}/notifications")
    public ResponseEntity<List<Notification>> getAllNotificationsForAUser(HttpServletRequest request, @PathVariable("userId") long userId) {
        throw new UnsupportedOperationException();
    }

    @PostMapping("/api/users/{userId}/notifications")
    public ResponseEntity acknownledgeNotifications(Principal principal, HttpServletRequest request, @RequestBody MessageAcknownledge messageAcknownledge) {
        throw new UnsupportedOperationException();
    }

}
