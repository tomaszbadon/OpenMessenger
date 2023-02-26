package net.bean.java.open.messenger.rest.resource;

import lombok.RequiredArgsConstructor;
import net.bean.java.open.messenger.rest.model.UserInfo;
import net.bean.java.open.messenger.service.CurrentUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class UserResource {

    private final CurrentUserService currentUserService;

    @GetMapping("/api/users/current")
    public ResponseEntity getUser(HttpServletRequest request) {
        UserInfo userInfo = currentUserService.getUserInfoFromToken(request).get();
        return ResponseEntity.ok(userInfo);
    }
}
