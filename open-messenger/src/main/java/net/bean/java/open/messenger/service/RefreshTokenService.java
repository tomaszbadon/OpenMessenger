package net.bean.java.open.messenger.service;

import io.vavr.control.Try;
import net.bean.java.open.messenger.rest.model.token.TokensInfo;

public interface RefreshTokenService {

    Try<TokensInfo> createAccessToken(Try<String> refreshToken, String url);

}
