package net.bean.java.open.messenger.util;

import net.bean.java.open.messenger.rest.exception.InvalidTokenException;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class HttpServletRequestUtil {

    public static final String BEARER = "Bearer ";

    public static String getToken(HttpServletRequest request) {
        String authorizationToken = request.getHeader(AUTHORIZATION);
        if(authorizationToken != null && authorizationToken.startsWith(BEARER)) {
            return authorizationToken.substring(BEARER.length());
        } else {
            throw InvalidTokenException.of();
        }

    }
}
