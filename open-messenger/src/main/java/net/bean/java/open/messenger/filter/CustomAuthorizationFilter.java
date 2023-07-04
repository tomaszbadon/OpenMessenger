package net.bean.java.open.messenger.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bean.java.open.messenger.exception.InternalException;
import net.bean.java.open.messenger.rest.exception.InvalidTokenException;
import net.bean.java.open.messenger.service.JwtTokenService;
import net.bean.java.open.messenger.util.HttpServletRequestUtil;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@Slf4j
@RequiredArgsConstructor
public class CustomAuthorizationFilter extends OncePerRequestFilter {

    private final JwtTokenService jwtTokenService;

    private final AllowedPathChecker allowedPathChecker;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(allowedPathChecker.isPathAllowedWithoutToken(request.getServletPath())) {
            filterChain.doFilter(request, response);
        } else {
            check(request, response, filterChain);
        }
    }

    private void check(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException {
        HttpServletRequestUtil.getToken(request)
                              .onFailure(t -> filter(request, response, filterChain))
                              .flatMap(jwtTokenService::getUsernamePasswordAuthenticationToken)
                              .andThen(authenticationToken -> {
                                  SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                                  filter(request, response, filterChain);
                              });

//        try {
//            String token = HttpServletRequestUtil.getToken(request).get();
//            if(StringUtils.hasText(token)) {
//                UsernamePasswordAuthenticationToken authenticationToken = jwtTokenService.getUsernamePasswordAuthenticationToken(token).get();
//                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//                filterChain.doFilter(request, response);
//            } else {
//                filterChain.doFilter(request, response);
//            }
//        } catch(InvalidTokenException | ServletException ex) {
//            response.setHeader("error", ex.getMessage());
//            response.setStatus(FORBIDDEN.value());
//            Map<String, String> error = new HashMap<>();
//            error.put("error_message", ex.getMessage());
//            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//            new ObjectMapper().writeValue(response.getOutputStream(), error);
//        }
    }

    private void filter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        try {
            filterChain.doFilter(request, response);
        } catch (IOException | ServletException e) {
            log.error(e.getMessage(), e);
            //TODO
            throw new InternalException("ble");
        }
    }
}