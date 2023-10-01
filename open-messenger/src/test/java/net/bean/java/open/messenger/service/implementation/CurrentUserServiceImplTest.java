package net.bean.java.open.messenger.service.implementation;

import io.vavr.control.Try;
import net.bean.java.open.messenger.exception.UserNotFoundException;
import net.bean.java.open.messenger.service.JwtTokenService;
import net.bean.java.open.messenger.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CurrentUserServiceImplTest {

    private final static String RANDOM_USER_NAME = "john.doe";

    private final static String DUMMY_TOKEN = "<TOKEN>";

    @Mock
    private UserService userService;

    @Mock
    private JwtTokenService jwtTokenService;

    @InjectMocks
    private CurrentUserServiceImpl currentUserService;

    @Test
    void getUserFromTokenWithUserNotFoundException() {
        when(jwtTokenService.getUserName(DUMMY_TOKEN)).thenReturn(Try.success(RANDOM_USER_NAME));
        when(userService.getUserByUserName(RANDOM_USER_NAME)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> currentUserService.tryToGetUserFromToken(Try.success(DUMMY_TOKEN)).get());
    }


}
