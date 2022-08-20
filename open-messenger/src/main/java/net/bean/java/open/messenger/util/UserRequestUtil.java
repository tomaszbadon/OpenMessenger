package net.bean.java.open.messenger.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class UserRequestUtil {

    public static final String BEARER = "Bearer ";

    public static String getUserFromHttpServletRequest(HttpServletRequest request) {
        String authorizationToken = request.getHeader(AUTHORIZATION);
        if(authorizationToken != null && authorizationToken.startsWith(BEARER)) {
            String token = authorizationToken.substring(BEARER.length());
            Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            String user = decodedJWT.getSubject();
            return user;
        } else {
            throw new RuntimeException("Cannot get User info from HttpServletRequest. The token is null or it do not contain " + BEARER);
        }
    }
}
