package net.bean.java.open.messenger.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class LoginInfo {
    private final String accessToken, refreshToken;
    private final long userId;
}
