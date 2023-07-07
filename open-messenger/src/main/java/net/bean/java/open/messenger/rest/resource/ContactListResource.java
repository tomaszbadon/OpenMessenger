package net.bean.java.open.messenger.rest.resource;

import lombok.RequiredArgsConstructor;
import net.bean.java.open.messenger.rest.model.ContactList;
import net.bean.java.open.messenger.service.ContactService;
import net.bean.java.open.messenger.service.CurrentUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class ContactListResource {
    private final CurrentUserService currentUserService;

    private final ContactService contactService;

    @GetMapping("/api/users/current/contacts")
    public ResponseEntity<ContactList> getContacts(HttpServletRequest request) {
        var user = currentUserService.tryToGetUserFromToken(request);
        return contactService.getContacts(user).map(list -> ResponseEntity.ok().body(list)).get();
    }
}
