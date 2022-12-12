package net.bean.java.open.messenger.resource;

import lombok.RequiredArgsConstructor;
import net.bean.java.open.messenger.entity.ContactInfo;
import net.bean.java.open.messenger.model.jpa.User;
import net.bean.java.open.messenger.service.CurrentUserService;
import net.bean.java.open.messenger.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ContactListResource {

    private final CurrentUserService currentUserService;

    private final UserService userService;

    @GetMapping("/api/users/current/contacts")
    public ResponseEntity getContacts(HttpServletRequest request) {

        User user = currentUserService.getUserFromTokenOrElseThrowException(request);

        List<ContactInfo> contactList = userService.getUsers().stream()
                                    .filter(u -> !u.equals(user))
                                    .map(ContactInfo::new)
                                    .collect(Collectors.toList());
        return ResponseEntity.ok().body(contactList);
    }
}
