package net.bean.java.open.messenger.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bean.java.open.messenger.data.jpa.model.User;
import net.bean.java.open.messenger.entity.Contact;
import net.bean.java.open.messenger.service.UserService;
import net.bean.java.open.messenger.util.UserRequestUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/contacts-api")
@Slf4j
public class ContactListResource {

    private final UserService userService;

    @GetMapping("/contacts")
    public ResponseEntity<List<Contact>> getContacts(HttpServletRequest request) {
        User curentUser = userService.getUser(UserRequestUtil.getUserFromHttpServletRequest(request)).get();
        List<Contact> contactList = userService.getUsers().stream()
                .filter(user -> !user.equals(curentUser))
                .map(Contact::of)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(contactList);
    }
}
