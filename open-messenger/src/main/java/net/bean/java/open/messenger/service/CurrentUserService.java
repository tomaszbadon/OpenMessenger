package net.bean.java.open.messenger.service;

import io.vavr.control.Try;
import net.bean.java.open.messenger.model.User;
import net.bean.java.open.messenger.rest.model.UserInfo;

import javax.servlet.http.HttpServletRequest;

public interface CurrentUserService {

    Try<User> tryToGetUserFromToken(HttpServletRequest httpServletRequest);

    Try<User> tryToGetUserFromToken(Try<String> token);

    Try<UserInfo> tryToGetUserInfoFromToken(HttpServletRequest httpServletRequest);

}