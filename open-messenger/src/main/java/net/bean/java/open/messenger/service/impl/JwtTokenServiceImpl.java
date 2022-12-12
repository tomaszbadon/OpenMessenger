package net.bean.java.open.messenger.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import net.bean.java.open.messenger.entity.Token;
import net.bean.java.open.messenger.entity.TokenType;
import net.bean.java.open.messenger.entity.TokensInfo;
import net.bean.java.open.messenger.exception.InvalidTokenException;
import net.bean.java.open.messenger.service.JwtTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@Service
@Slf4j
public class JwtTokenServiceImpl implements JwtTokenService {

    private static final String ROLES = "roles";

    private static final String JWT_PASSWORD_PROPERTY = "application.jwt.password";

    @Value("${application.jwt.access.token.duration}")
    private String accessTokenDuration;

    @Value("${application.jwt.refresh.token.duration}")
    private String refreshTokenDuration;

    private final Algorithm algorithm;

    private final JWTVerifier verifier;

    @Autowired
    public JwtTokenServiceImpl(Environment environment) {
        String password = Optional.ofNullable(environment.getProperty(JWT_PASSWORD_PROPERTY))
                                  .orElseThrow(() -> new RuntimeException(String.format("Cannot find property: '%s' in application.properties", JWT_PASSWORD_PROPERTY)));
        algorithm = Algorithm.HMAC256(password.getBytes());
        verifier = JWT.require(algorithm).build();
    }

    @Override
    public TokensInfo createTokensInfo(User user, String requestUrl) {
        List<Token> tokens = List.of(createJwtToken(TokenType.ACCESS_TOKEN, user, requestUrl, Integer.parseInt(accessTokenDuration)),
                                     createJwtToken(TokenType.REFRESH_TOKEN, user, requestUrl, Integer.parseInt(refreshTokenDuration)));
        return new TokensInfo(tokens);
    }

    @Override
    public String getUserName(String token) {
        try {
            DecodedJWT decodedJWT = verifier.verify(token);
            return decodedJWT.getSubject();
        } catch (JWTVerificationException e) {
            log.error("Could not get username from token", e);
            throw InvalidTokenException.of(e);
        }
    }

    @Override
    public UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationToken(String token) {
        try {
            DecodedJWT decodedJWT = verifier.verify(token);
            String user = decodedJWT.getSubject();
            String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            stream(roles).forEach(role -> {
                authorities.add(new SimpleGrantedAuthority(role));
            });
            return new UsernamePasswordAuthenticationToken(user, null, authorities);
        } catch (TokenExpiredException e) {
            throw InvalidTokenException.of(e);
        }
    }

    private Token createJwtToken(TokenType tokenType, User user, String requestUrl, int duration) {
        String token = JWT.create().withSubject(user.getUsername())
                  .withExpiresAt(new Date(System.currentTimeMillis() + (duration * 60 * 1000)))
                  .withIssuer(requestUrl)
                  .withClaim(ROLES, user.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                                                                  .collect(Collectors.toList()))
                  .sign(algorithm);
        return new Token(tokenType, token);
    }



}