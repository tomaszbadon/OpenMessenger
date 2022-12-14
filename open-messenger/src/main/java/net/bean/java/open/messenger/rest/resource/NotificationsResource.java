package net.bean.java.open.messenger.rest.resource;

import lombok.RequiredArgsConstructor;
import net.bean.java.open.messenger.rest.model.Notification;
import net.bean.java.open.messenger.model.entity.User;
import net.bean.java.open.messenger.rest.model.MessageAcknownledge;
import net.bean.java.open.messenger.service.MessageService;
import net.bean.java.open.messenger.service.NotificationSerivce;
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

    private final NotificationSerivce notificationsService;
    private final UserService userService;
    private final MessageService messageService;

    @GetMapping("/api/users/{userId}/notifications")
    public ResponseEntity<List<Notification>> getAllNotificationsForAUser(HttpServletRequest request, @PathVariable("userId") long userId) {
        return ResponseEntity.ok().body(notificationsService.getNotification(userId));
    }

    @PostMapping("/api/users/{userId}/notifications")
    public ResponseEntity acknownledgeNotifications(Principal principal, HttpServletRequest request, @RequestBody MessageAcknownledge messageAcknownledge) {
        User user = userService.getUser(principal.getName()).get();
        messageService.acknowledgedMessages(user.getId(), messageAcknownledge.getNotificationsToAcknowledge());
        return ResponseEntity.noContent().build();
    }

}
