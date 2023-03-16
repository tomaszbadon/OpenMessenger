package net.bean.java.open.messenger.service;

import io.vavr.control.Try;
import net.bean.java.open.messenger.model.User;
import net.bean.java.open.messenger.rest.model.UserInfo;

import javax.servlet.http.HttpServletRequest;

public interface CurrentUserService {

    Try<User> getUserFromToken(HttpServletRequest httpServletRequest);

    Try<User> getUserFromToken(String token);

    Try<UserInfo> getUserInfoFromToken(HttpServletRequest httpServletRequest);

}