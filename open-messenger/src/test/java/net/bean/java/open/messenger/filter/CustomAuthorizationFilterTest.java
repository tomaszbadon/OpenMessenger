package net.bean.java.open.messenger.filter;

import io.vavr.control.Try;
import net.bean.java.open.messenger.rest.exception.InvalidTokenException;
import net.bean.java.open.messenger.service.JwtTokenService;
import net.bean.java.open.messenger.util.HttpServletRequestUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomAuthorizationFilterTest {

    @Mock
    private JwtTokenService jwtTokenService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken;

    @Test
    @DisplayName("It tests if the filter handles a request correctly with valid token")
    void doFilterInternalWithValidToken() throws IOException, ServletException {

        try (MockedStatic<HttpServletRequestUtil> httpServletRequestUtil = Mockito.mockStatic(HttpServletRequestUtil.class);
             MockedStatic<SecurityContextHolder> securityContextHolder = Mockito.mockStatic(SecurityContextHolder.class)) {

            httpServletRequestUtil.when(() -> HttpServletRequestUtil.getToken(request)).thenReturn(Try.success("token"));
            when(jwtTokenService.getUsernamePasswordAuthenticationToken("token")).thenReturn(Try.success(usernamePasswordAuthenticationToken));
            securityContextHolder.when(() -> SecurityContextHolder.getContext()).thenReturn(securityContext);

            CustomAuthorizationFilter customAuthorizationFilter = new CustomAuthorizationFilter(jwtTokenService);
            customAuthorizationFilter.doFilterInternal(request, response, filterChain);

            verify(filterChain, times(1)).doFilter(request, response);
            verify(securityContext, times(1)).setAuthentication(usernamePasswordAuthenticationToken);
        }
    }

    @Test
    @DisplayName("It tests if the filter handles a request correctly with no token")
    void doFilterInternalWithNoToken() throws IOException, ServletException {
        try (MockedStatic<HttpServletRequestUtil> httpServletRequestUtil = Mockito.mockStatic(HttpServletRequestUtil.class)) {
            httpServletRequestUtil.when(() -> HttpServletRequestUtil.getToken(request)).thenReturn(Try.failure(new InvalidTokenException()));

            CustomAuthorizationFilter customAuthorizationFilter = new CustomAuthorizationFilter(jwtTokenService);
            customAuthorizationFilter.doFilterInternal(request, response, filterChain);

            verify(filterChain, times(1)).doFilter(request, response);
            verifyNoInteractions(securityContext);
            verifyNoInteractions(jwtTokenService);
        }
    }

    @Test
    @DisplayName("It tests if the filter handles a request correctly with invalid token")
    void doFilterInternalWithInvalidToken() throws IOException, ServletException {
        try (MockedStatic<HttpServletRequestUtil> httpServletRequestUtil = Mockito.mockStatic(HttpServletRequestUtil.class)) {

            httpServletRequestUtil.when(() -> HttpServletRequestUtil.getToken(request)).thenReturn(Try.success("token"));
            when(jwtTokenService.getUsernamePasswordAuthenticationToken("token")).thenReturn(Try.failure(new InvalidTokenException()));

            CustomAuthorizationFilter customAuthorizationFilter = new CustomAuthorizationFilter(jwtTokenService);
            customAuthorizationFilter.doFilterInternal(request, response, filterChain);

            verifyNoInteractions(securityContext);
            verifyNoInteractions(filterChain);
        }
    }

}
