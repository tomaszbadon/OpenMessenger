package net.bean.java.open.messenger.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bean.java.open.messenger.data.jpa.model.User;
import net.bean.java.open.messenger.service.UserService;
import net.bean.java.open.messenger.util.UserRequestUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Slf4j
public class UserResource {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<User> getUser(HttpServletRequest request) {
        Optional<User> curentUser = userService.getUser(UserRequestUtil.getUserFromHttpServletRequest(request));
        return ResponseEntity.ok(curentUser.orElseThrow());
    }


}
