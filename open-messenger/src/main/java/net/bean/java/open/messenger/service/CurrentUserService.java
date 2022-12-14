package net.bean.java.open.messenger.service;

import net.bean.java.open.messenger.rest.model.UserInfo;
import net.bean.java.open.messenger.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public interface CurrentUserService {

    Optional<User> getUserFromToken(HttpServletRequest httpServletRequest);

    Optional<UserInfo> getUserInfoFromToken(HttpServletRequest httpServletRequest);

    User getUserFromTokenOrElseThrowException(HttpServletRequest httpServletRequest);

    UserInfo getUserInfoFromTokenOrElseThrowException(HttpServletRequest httpServletRequest);


}