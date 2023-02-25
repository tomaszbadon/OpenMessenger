package net.bean.java.open.messenger.service.implementation;

import net.bean.java.open.messenger.model.entity.Role;
import net.bean.java.open.messenger.model.entity.User;
import net.bean.java.open.messenger.repository.UserRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

import java.text.MessageFormat;
import java.util.List;

import static net.bean.java.open.messenger.rest.exception.ExceptionConstants.CANNOT_FIND_USER_IN_REPOSITORY;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private User user;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    protected void loadUserByUserName() {
        when(user.getUserName()).thenReturn("john.doe");
        when(user.getPassword()).thenReturn("12345678");
        when(user.getRoles()).thenReturn(List.of(new Role(1L, "ROLE_USER")));
        when(userRepository.findByUserName("john.doe")).thenReturn(user);
        UserDetails userDetails = userService.loadUserByUsername("john.doe");

        Assertions.assertEquals("john.doe", userDetails.getUsername());
        Assertions.assertEquals("12345678", userDetails.getPassword());
        Assertions.assertEquals(1, userDetails.getAuthorities().size());
    }

    @Test
    protected void loadUserByUserNameWithException() {
        when(userRepository.findByUserName("john.doe")).thenReturn(null);

        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("john.doe"));
        String message = MessageFormat.format(CANNOT_FIND_USER_IN_REPOSITORY, "john.doe");
        Assertions.assertEquals(message, exception.getMessage());
    }



}
