package net.bean.java.open.messenger.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bean.java.open.messenger.data.jpa.model.User;
import net.bean.java.open.messenger.service.UserService;
import net.bean.java.open.messenger.util.UserRequestUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserResource {

    private final UserService userService;

    @GetMapping("/api/users/{userId}")
    public ResponseEntity getUser(HttpServletRequest request, @PathVariable("userId") long userId) {
        User user = userService.getUser(UserRequestUtil.getUserFromHttpServletRequest(request)).get();
        if(userId != user.getId()) {
            return new ResponseEntity<String>("You are unauthorized to query data belonging to another user.", HttpStatus.UNAUTHORIZED);
        }


        return ResponseEntity.ok(user);
    }


}
