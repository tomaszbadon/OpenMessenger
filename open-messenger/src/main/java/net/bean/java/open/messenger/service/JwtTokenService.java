package net.bean.java.open.messenger.service;

import net.bean.java.open.messenger.entity.TokensInfo;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;

public interface JwtTokenService {

    TokensInfo createTokensInfo(User user, String requestUrl);

    String getUserName(String token);

    UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationToken(String token);

}
