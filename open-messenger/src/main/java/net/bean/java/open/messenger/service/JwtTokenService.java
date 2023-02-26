package net.bean.java.open.messenger.service;

import io.vavr.control.Try;
import net.bean.java.open.messenger.rest.model.TokensInfo;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;

public interface JwtTokenService {

    TokensInfo createTokensInfo(User user, String requestUrl);

    TokensInfo createTokensInfo(net.bean.java.open.messenger.model.entity.User user, String requestUrl);

    Try<String> getUserName(String token);

    UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationToken(String token);

}
