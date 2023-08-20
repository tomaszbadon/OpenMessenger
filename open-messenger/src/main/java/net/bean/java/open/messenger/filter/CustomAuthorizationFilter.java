package net.bean.java.open.messenger.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bean.java.open.messenger.exception.InternalException;
import net.bean.java.open.messenger.service.JwtTokenService;
import net.bean.java.open.messenger.util.HttpServletRequestUtil;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static net.bean.java.open.messenger.exception.ExceptionConstants.CANNOT_PROCESS_REQUEST;

@Slf4j
@RequiredArgsConstructor
public class CustomAuthorizationFilter extends OncePerRequestFilter {

    private final JwtTokenService jwtTokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        HttpServletRequestUtil.getToken(request)
                .onFailure(t -> filter(request, response, filterChain))
                .flatMap(jwtTokenService::getUsernamePasswordAuthenticationToken)
                .andThen(authenticationToken -> {
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filter(request, response, filterChain);
                });
    }

    private void filter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        try {
            filterChain.doFilter(request, response);
        } catch (IOException | ServletException e) {
            log.error(e.getMessage(), e);
            throw new InternalException(CANNOT_PROCESS_REQUEST);
        }
    }
}