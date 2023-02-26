package net.bean.java.open.messenger.service;

import io.vavr.control.Try;
import net.bean.java.open.messenger.rest.model.UserInfo;
import net.bean.java.open.messenger.model.entity.User;

import javax.servlet.http.HttpServletRequest;

public interface CurrentUserService {

    //Optional<User> getUserFromToken(HttpServletRequest httpServletRequest);

    //Optional<User> getUserFromToken(String token);

    Try<User> getUserFromToken(HttpServletRequest httpServletRequest);

    Try<User> getUserFromToken(String token);

    Try<UserInfo> getUserInfoFromToken(HttpServletRequest httpServletRequest);


}