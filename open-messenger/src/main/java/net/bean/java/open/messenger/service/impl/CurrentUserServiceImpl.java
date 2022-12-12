package net.bean.java.open.messenger.service.impl;

import lombok.RequiredArgsConstructor;
import net.bean.java.open.messenger.exception.ExceptionConstants;
import net.bean.java.open.messenger.model.jpa.User;
import net.bean.java.open.messenger.service.CurrentUserService;
import net.bean.java.open.messenger.service.JwtTokenService;
import net.bean.java.open.messenger.service.UserService;
import net.bean.java.open.messenger.util.HttpServletRequestUtil;
import net.bean.java.open.messenger.util.LazyException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CurrentUserServiceImpl implements CurrentUserService {

    private final UserService userService;

    private final JwtTokenService jwtTokenService;

    @Override
    public Optional<User> getUserFromToken(HttpServletRequest httpServletRequest) {
        String token = HttpServletRequestUtil.getToken(httpServletRequest);
        String userName = jwtTokenService.getUserName(token);
        return userService.getUser(userName);
    }

    @Override
    public User getUserFromTokenOrElseThrowException(HttpServletRequest httpServletRequest) {
        return getUserFromToken(httpServletRequest)
                .orElseThrow(LazyException.lazyRuntimeException(ExceptionConstants.CANNOT_FIND_USER_IN_REPOSITORY));
    }

}