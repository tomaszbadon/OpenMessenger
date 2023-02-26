package net.bean.java.open.messenger.service.implementation;

import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import net.bean.java.open.messenger.model.entity.User;
import net.bean.java.open.messenger.rest.exception.ExceptionConstants;
import net.bean.java.open.messenger.rest.exception.UserNotFoundException;
import net.bean.java.open.messenger.rest.model.UserInfo;
import net.bean.java.open.messenger.service.CurrentUserService;
import net.bean.java.open.messenger.service.JwtTokenService;
import net.bean.java.open.messenger.service.UserService;
import net.bean.java.open.messenger.util.HttpServletRequestUtil;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class CurrentUserServiceImpl implements CurrentUserService {

    private final UserService userService;

    private final JwtTokenService jwtTokenService;

    @Override
    public Try<User> getUserFromToken(String token) {
        return jwtTokenService.getUserName(token)
                              .map(userName -> userService.getUser(userName)
                                                          .orElseThrow(() -> new UserNotFoundException(ExceptionConstants.CANNOT_GET_USER_FROM_TOKEN)));
    }

    @Override
    public Try<User> getUserFromToken(HttpServletRequest httpServletRequest) {
        return HttpServletRequestUtil.getToken(httpServletRequest).flatMap((this::getUserFromToken));
    }

    @Override
    public Try<UserInfo> getUserInfoFromToken(HttpServletRequest httpServletRequest) {
        return getUserFromToken(httpServletRequest).map(UserInfo::new);
    }

}
