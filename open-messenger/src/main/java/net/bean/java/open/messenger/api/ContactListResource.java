package net.bean.java.open.messenger.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bean.java.open.messenger.annotation.Owner;
import net.bean.java.open.messenger.data.jpa.model.User;
import net.bean.java.open.messenger.entity.Contact;
import net.bean.java.open.messenger.service.UserService;
import net.bean.java.open.messenger.util.UserRequestUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ContactListResource {

    private final UserService userService;

    @GetMapping("/api/users/{userId}/contacts")
    public ResponseEntity getContacts(HttpServletRequest request, @Owner @PathVariable("userId") long userId) {
        User user = userService.getUser(UserRequestUtil.getUserFromHttpServletRequest(request)).get();
        if(userId == user.getId()) {
            List<Contact> contactList = userService.getUsers().stream()
                    .filter(u -> !u.equals(user))
                    .map(Contact::of)
                    .collect(Collectors.toList());
            return ResponseEntity.ok().body(contactList);
        } else {
            return new ResponseEntity<String>("You are unauthorized to query data belonging to another user.", HttpStatus.UNAUTHORIZED);
        }
    }
}
