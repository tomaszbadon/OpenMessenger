package net.bean.java.open.messenger.service;

import net.bean.java.open.messenger.model.jpa.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public interface CurrentUserService {

    Optional<User> getUserFromToken(HttpServletRequest httpServletRequest);

    User getUserFromTokenOrElseThrowException(HttpServletRequest httpServletRequest);

}