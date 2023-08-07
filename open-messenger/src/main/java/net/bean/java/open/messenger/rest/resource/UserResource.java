package net.bean.java.open.messenger.rest.resource;

import lombok.RequiredArgsConstructor;
import net.bean.java.open.messenger.rest.model.user.NewUserInfo;
import net.bean.java.open.messenger.rest.model.user.UserInfo;
import net.bean.java.open.messenger.rest.resource.patch.PatchOperations;
import net.bean.java.open.messenger.service.CurrentUserService;
import net.bean.java.open.messenger.service.UserService;
import net.bean.java.open.messenger.service.implementation.PatchCurrentUserServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequiredArgsConstructor
public class UserResource {

    private final CurrentUserService currentUserService;

    private final UserService userService;

    private final PatchCurrentUserServiceImpl patchCurrentUserService;

    @GetMapping("/api/users/current")
    public ResponseEntity<UserInfo> getUser(HttpServletRequest request) {
        return currentUserService.tryToGetUserInfoFromToken(request)
                                 .map(ResponseEntity::ok).get();
    }

    @PostMapping(value = "/api/users", consumes = "application/json")
    public ResponseEntity<UserInfo> createUser(HttpServletRequest request, @RequestBody @Valid NewUserInfo newUserInfo) {
        return userService.tryToCreateUser(newUserInfo).map(userInfo -> {
            URI location = URI.create("/api/users/current");
            return ResponseEntity.created(location).body(userInfo);
        }).get();
    }

    @PatchMapping(value = "/api/users/current", consumes = "application/json")
    public ResponseEntity<UserInfo> patchUser(HttpServletRequest request, @RequestBody PatchOperations operations) {
        return patchCurrentUserService.updateUser(request, operations)
                                      .map(ResponseEntity::ok).get();
    }

}
