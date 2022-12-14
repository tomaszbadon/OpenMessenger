package net.bean.java.open.messenger.rest.resource;

import lombok.RequiredArgsConstructor;
import net.bean.java.open.messenger.rest.model.ContactInfo;
import net.bean.java.open.messenger.model.entity.User;
import net.bean.java.open.messenger.service.ContactResource;
import net.bean.java.open.messenger.service.CurrentUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ContactListResource {

    private final CurrentUserService currentUserService;

    private final ContactResource contactResource;

    @GetMapping("/api/users/current/contacts")
    public ResponseEntity getContacts(HttpServletRequest request) {
        User user = currentUserService.getUserFromTokenOrElseThrowException(request);
        List<ContactInfo> contactList = contactResource.getContacts(user);
        return ResponseEntity.ok().body(contactList);
    }
}
