package net.bean.java.open.messenger.filter;

import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class AllowedPathChecker {

    //TODO: Improve how it is being checked!

    private final Set<String> allowedPaths = Set.of("/login", "/token/refresh/**", "/",
            "/webjars/jquery/3.1.1/jquery.min.js",
            "/webjars/sockjs-client/1.0.2/sockjs.min.js",
            "/webjars/stomp-websocket/2.3.3/stomp.min.js");

    public boolean isPathAllowedWithoutToken(String path) {
        return allowedPaths.contains(path);
    }

}
