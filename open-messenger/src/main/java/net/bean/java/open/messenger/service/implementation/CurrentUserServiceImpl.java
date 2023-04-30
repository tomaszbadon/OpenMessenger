package net.bean.java.open.messenger.service.implementation;

import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import net.bean.java.open.messenger.model.User;
import net.bean.java.open.messenger.exception.ExceptionConstants;
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
    public Try<User> tryToGetUserFromToken(Try<String> token) {
        return jwtTokenService.tryToGetUserName(token)
                              .map(userName -> userService.getUserByUserName(userName)
                                                          .orElseThrow(() -> new UserNotFoundException(ExceptionConstants.CANNOT_GET_USER_FROM_TOKEN)));
    }

    @Override
    public Try<User> tryToGetUserFromToken(HttpServletRequest httpServletRequest) {
        var token = HttpServletRequestUtil.getToken(httpServletRequest);
        return tryToGetUserFromToken(token);
    }

    @Override
    public Try<UserInfo> tryToGetUserInfoFromToken(HttpServletRequest httpServletRequest) {
        return tryToGetUserFromToken(httpServletRequest).map(UserInfo::new);
    }

}
