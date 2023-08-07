package net.bean.java.open.messenger.service;

import io.vavr.control.Try;
import net.bean.java.open.messenger.rest.model.token.TokensInfo;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;

public interface JwtTokenService {

    TokensInfo createTokensInfo(User user, String requestUrl);

    TokensInfo createTokensInfo(net.bean.java.open.messenger.model.User user, String requestUrl);

    Try<String> tryToGetUserName(Try<String> token);

    Try<UsernamePasswordAuthenticationToken> getUsernamePasswordAuthenticationToken(String token);

}
