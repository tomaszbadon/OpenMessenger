package net.bean.java.open.messenger.rest.resource;

import lombok.RequiredArgsConstructor;
import net.bean.java.open.messenger.rest.model.token.TokensInfo;
import net.bean.java.open.messenger.service.RefreshTokenService;
import net.bean.java.open.messenger.util.HttpServletRequestUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class AuthorisationResource {

    private final RefreshTokenService refreshTokenService;

    @GetMapping("/api/auth/accessToken")
    public ResponseEntity<TokensInfo> refreshToken(HttpServletRequest request) {
        return refreshTokenService.createAccessToken(HttpServletRequestUtil.getToken(request), request.getRequestURL().toString())
                                  .map(ResponseEntity::ok)
                                  .get();
    }

}
