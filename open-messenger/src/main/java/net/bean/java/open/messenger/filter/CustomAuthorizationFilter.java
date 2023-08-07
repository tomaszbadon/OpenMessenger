package net.bean.java.open.messenger.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bean.java.open.messenger.exception.InternalException;
import net.bean.java.open.messenger.rest.exception.InvalidTokenException;
import net.bean.java.open.messenger.service.JwtTokenService;
import net.bean.java.open.messenger.util.HttpServletRequestUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.Predicates.instanceOf;
import static net.bean.java.open.messenger.exception.ExceptionConstants.CANNOT_PROCESS_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@Slf4j
@RequiredArgsConstructor
public class CustomAuthorizationFilter extends OncePerRequestFilter {

    private final JwtTokenService jwtTokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException  {
        //noinspection unchecked
        HttpServletRequestUtil.getToken(request)
                .onFailure(InvalidTokenException.class, e -> {
                    //If there is no token, then filtering is processed
                    filter(request, response, filterChain);
                })
                .flatMap(jwtTokenService::getUsernamePasswordAuthenticationToken)
                .onFailure(InvalidTokenException.class, e -> {
                    //If the token is invalid, then we send error to a client
                    logExceptionToResponse(response, e, HttpStatus.FORBIDDEN);
                })
                //If the token is correct, then we set the security context process filtering
                    .andThen(SecurityContextHolder.getContext()::setAuthentication)
                .andThen(() -> filter(request, response, filterChain));
    }

    private void filter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        //noinspection unchecked
        Try.of(() -> {
            filterChain.doFilter(request, response);
            return null;
        }).mapFailure(
                Case($(instanceOf(IOException.class)), t -> new InternalException(CANNOT_PROCESS_REQUEST)),
                Case($(instanceOf(ServletException.class)), t -> new InternalException(CANNOT_PROCESS_REQUEST))
        ).onFailure(InternalException.class, (e) -> {
            logExceptionToResponse(response, e, HttpStatus.INTERNAL_SERVER_ERROR);
        });
    }

    private void logExceptionToResponse(HttpServletResponse response, Exception exception, HttpStatus status) {
        Try.of(() -> {
            log.error("The request could not be process because of: " + exception.getMessage(), exception);
            response.setHeader("error", "Error");
            response.setStatus(status.value());
            Map<String, String> error = new HashMap<>();
            error.put("error_message", exception.getMessage());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), error);
            return null;
        });
    }


}