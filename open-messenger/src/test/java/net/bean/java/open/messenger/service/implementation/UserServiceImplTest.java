package net.bean.java.open.messenger.service.implementation;

import net.bean.java.open.messenger.model.enums.Role;
import net.bean.java.open.messenger.model.User;
import net.bean.java.open.messenger.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

import static net.bean.java.open.messenger.exception.ExceptionConstants.CANNOT_FIND_USER_IN_REPOSITORY;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void loadUserByUserName() {
        User user = new User();
        user.setUserName("john.doe");
        user.setPassword("12345678");
        user.setRoles(List.of(Role.ROLE_USER));
        when(userRepository.findByUserName("john.doe")).thenReturn(Optional.of(user));
        UserDetails userDetails = userService.loadUserByUsername("john.doe");

        Assertions.assertEquals("john.doe", userDetails.getUsername());
        Assertions.assertEquals("12345678", userDetails.getPassword());
        Assertions.assertEquals(1, userDetails.getAuthorities().size());
    }

    @Test
    void loadUserByUserNameWithException() {
        when(userRepository.findByUserName("john.doe")).thenReturn(Optional.empty());
        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("john.doe"));
        String message = MessageFormat.format(CANNOT_FIND_USER_IN_REPOSITORY, "john.doe");
        Assertions.assertEquals(message, exception.getMessage());
    }



}
