package net.bean.java.open.messenger.service;

import io.vavr.control.Try;
import net.bean.java.open.messenger.rest.model.token.TokenType;
import net.bean.java.open.messenger.rest.model.token.TokensInfo;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;

public interface JwtTokenService {

    TokensInfo createAccessAndRefreshTokens(User user, String requestUrl);

    TokensInfo createSingleToken(TokenType tokenType, User user, String requestUrl);

    TokensInfo createSingleToken(TokenType tokenType, net.bean.java.open.messenger.model.User user, String requestUrl);

    Try<String> getUserName(String token);

    Try<UsernamePasswordAuthenticationToken> getUsernamePasswordAuthenticationToken(String token);

}
