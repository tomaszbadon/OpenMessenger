package net.bean.java.open.messenger.rest.resource;

import lombok.RequiredArgsConstructor;
import net.bean.java.open.messenger.rest.model.UserInfo;
import net.bean.java.open.messenger.rest.resource.patch.PatchOperations;
import net.bean.java.open.messenger.service.CurrentUserService;
import net.bean.java.open.messenger.service.implementation.PatchCurrentUserServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class UserResource {

    private final CurrentUserService currentUserService;

    private final PatchCurrentUserServiceImpl patchCurrentUserService;

    @GetMapping("/api/users/current")
    public ResponseEntity<UserInfo> getUser(HttpServletRequest request) {
        return currentUserService.tryToGetUserInfoFromToken(request)
                                 .map(ResponseEntity::ok).get();
    }

    @PatchMapping(value = "/api/users/current", consumes = "application/json")
    public ResponseEntity<UserInfo> patchUser(HttpServletRequest request, @RequestBody PatchOperations operations) {
        return patchCurrentUserService.updateUser(request, operations)
                                      .map(ResponseEntity::ok).get();
    }

}
