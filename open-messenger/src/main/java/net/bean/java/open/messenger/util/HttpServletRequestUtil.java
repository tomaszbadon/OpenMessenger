package net.bean.java.open.messenger.util;

import io.vavr.control.Try;
import net.bean.java.open.messenger.rest.exception.InvalidTokenException;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class HttpServletRequestUtil {

    public static final String BEARER = "Bearer ";

    public static Try<String> getToken(HttpServletRequest request) {
        String authorizationToken = request.getHeader(AUTHORIZATION);
        if(StringUtils.isNotEmpty(authorizationToken) && authorizationToken.startsWith(BEARER)) {
            return Try.success(authorizationToken.substring(BEARER.length()));
        } else {
            return Try.failure(new InvalidTokenException());
        }
    }
}
