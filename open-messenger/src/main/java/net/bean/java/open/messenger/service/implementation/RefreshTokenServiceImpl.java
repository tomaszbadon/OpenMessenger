package net.bean.java.open.messenger.service.implementation;

import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import net.bean.java.open.messenger.rest.model.token.TokenType;
import net.bean.java.open.messenger.rest.model.token.TokensInfo;
import net.bean.java.open.messenger.service.JwtTokenService;
import net.bean.java.open.messenger.service.RefreshTokenService;
import net.bean.java.open.messenger.service.UserService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final UserService userService;

    private final JwtTokenService jwtTokenService;

    @Override
    public Try<TokensInfo> createAccessToken(Try<String> refreshToken, String url) {
        return refreshToken.flatMap(jwtTokenService::getUserName)
                           .flatMap(userService::tryToGetUserByUserName)
                           .map(user -> jwtTokenService.createSingleToken(TokenType.ACCESS_TOKEN, user, url));
    }
}
