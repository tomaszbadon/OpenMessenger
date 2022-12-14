package net.bean.java.open.messenger.service.implementation;

import lombok.RequiredArgsConstructor;
import net.bean.java.open.messenger.rest.model.UserInfo;
import net.bean.java.open.messenger.rest.exception.ExceptionConstants;
import net.bean.java.open.messenger.rest.exception.UserNotFoundException;
import net.bean.java.open.messenger.model.entity.User;
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
    public Optional<UserInfo> getUserInfoFromToken(HttpServletRequest httpServletRequest) {
        return getUserFromToken(httpServletRequest).map(user -> new UserInfo(user));
    }

    @Override
    public User getUserFromTokenOrElseThrowException(HttpServletRequest httpServletRequest) {
        return getUserFromToken(httpServletRequest)
                .orElseThrow(LazyException.lazyRuntimeException(new UserNotFoundException(ExceptionConstants.CANNOT_GET_USER_FROM_TOKEN)));
    }

    @Override
    public UserInfo getUserInfoFromTokenOrElseThrowException(HttpServletRequest httpServletRequest) {
        User user = getUserFromTokenOrElseThrowException(httpServletRequest);
        return new UserInfo(user);
    }

}