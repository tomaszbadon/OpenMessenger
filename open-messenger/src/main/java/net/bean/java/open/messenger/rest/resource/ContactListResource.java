package net.bean.java.open.messenger.rest.resource;

import lombok.RequiredArgsConstructor;
import net.bean.java.open.messenger.model.User;
import net.bean.java.open.messenger.rest.model.ContactList;
import net.bean.java.open.messenger.service.ContactResource;
import net.bean.java.open.messenger.service.CurrentUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class ContactListResource {
    private final CurrentUserService currentUserService;

    private final ContactResource contactResource;

    @GetMapping("/api/users/current/contacts")
    public ResponseEntity<ContactList> getContacts(HttpServletRequest request) {
        User user = currentUserService.getUserFromToken(request).get();
        return ResponseEntity.ok().body(contactResource.getContacts(user));
    }
}
