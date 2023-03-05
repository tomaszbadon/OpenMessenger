package net.bean.java.open.messenger.service.implementation;

import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bean.java.open.messenger.model.entity.User;
import net.bean.java.open.messenger.repository.UserRepository;
import net.bean.java.open.messenger.rest.exception.UserNotFoundException;
import net.bean.java.open.messenger.service.UserService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static net.bean.java.open.messenger.rest.exception.ExceptionConstants.CANNOT_FIND_USER_IN_REPOSITORY;

@Service
@Transactional
@Slf4j @RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username).stream().findFirst().orElseThrow(() -> {
            log.error("User '{}' not found in the database", username);
            return new UsernameNotFoundException(MessageFormat.format(CANNOT_FIND_USER_IN_REPOSITORY, username));
        });
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), authorities);
    }

    @Override
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userRepository.save(user);
        log.info("Save a User {} to the database with id: '{}'", user.getUserName(), user.getId());
        return user;
    }

    @Override
    public Optional<User> getUser(String userName) {
        return userRepository.findByUserName(userName).stream().findFirst();
    }

    @Override
    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    @Override
    public Try<User> tryToGetUser(String id) {
       return getUserById(id).map(Try::success)
               .orElseGet(() -> Try.failure(UserNotFoundException.withUserId(id)));
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }
}