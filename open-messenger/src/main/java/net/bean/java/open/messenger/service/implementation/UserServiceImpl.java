package net.bean.java.open.messenger.service.implementation;

import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bean.java.open.messenger.exception.InternalException;
import net.bean.java.open.messenger.exception.UserAlreadyExistsException;
import net.bean.java.open.messenger.exception.UserNotFoundException;
import net.bean.java.open.messenger.model.User;
import net.bean.java.open.messenger.repository.UserRepository;
import net.bean.java.open.messenger.rest.model.user.NewUserInfo;
import net.bean.java.open.messenger.rest.model.user.UserInfo;
import net.bean.java.open.messenger.service.MessagingManagementService;
import net.bean.java.open.messenger.service.UserService;
import net.bean.java.open.messenger.util.DefaultUsersAvatar;
import net.bean.java.open.messenger.util.UserQueueNameProvider;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.*;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.Predicates.instanceOf;
import static net.bean.java.open.messenger.exception.ExceptionConstants.CANNOT_FIND_USER_IN_REPOSITORY;
import static net.bean.java.open.messenger.exception.ExceptionConstants.CREATION_OF_THE_USER_WENT_WRONG;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserQueueNameProvider userQueueNameProvider;
    private final MessagingManagementService messagingManagementService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username).orElseThrow(() -> {
            log.error("User '{}' not found in the database", username);
            return new UsernameNotFoundException(MessageFormat.format(CANNOT_FIND_USER_IN_REPOSITORY, username));
        });
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.name())));
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), authorities);
    }

    @Override
    public Try<UserInfo> tryToCreateUser(NewUserInfo newUserInfo) {
        return tryToCreateUser(newUserInfo, Optional.empty());
    }

    @Override
    public Try<UserInfo> tryToCreateUser(NewUserInfo newUserInfo, String base64EncodedAvatar) {
        return tryToCreateUser(newUserInfo, Optional.of(base64EncodedAvatar));
    }

    private Try<UserInfo> tryToCreateUser(NewUserInfo newUserInfo, Optional<String> base64EncodedAvatar) {
        //noinspection unchecked
        return Try.of(() -> {
                    final User user = new User(newUserInfo, passwordEncoder);
                    messagingManagementService.createUser(user.getUserName(), user.getPassword());
                    messagingManagementService.assignUserToApplicationVirtualHost(user);
                    messagingManagementService.createQueue(userQueueNameProvider.createQueueName(user));
                    base64EncodedAvatar.ifPresent(user::setAvatar);
                    User createdUser = userRepository.save(user);
                    log.info("Save a User {} to the database with id: '{}'", user.getUserName(), user.getId());
                    return new UserInfo(createdUser);
                }).onFailure(t -> log.error(t.getMessage(), t))
                .mapFailure(
                        Case($(instanceOf(DuplicateKeyException.class)), t -> new UserAlreadyExistsException(newUserInfo.getUserName())),
                        Case($(instanceOf(Exception.class)), t -> new InternalException(MessageFormat.format(CREATION_OF_THE_USER_WENT_WRONG, newUserInfo.getUserName())))
                );
    }

    @Override
    public Optional<User> getUserByUserName(String userName) {
        return userRepository.findByUserName(userName).stream().findFirst();
    }

    @Override
    public Try<User> tryToGetUserByUserName(String userName) {
        return getUserByUserName(userName).map(Try::success)
                                          .orElseGet(() -> Try.failure(UserNotFoundException.withUserName(userName)));
    }

    @Override
    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    @Override
    public Try<User> tryToGetUserById(String id) {
        return getUserById(id).map(Try::success)
                              .orElseGet(() -> Try.failure(UserNotFoundException.withUserId(id)));
    }

    @Override
    public byte[] getAvatarByUserId(String userId) {
        String avatar = getUserById(userId)
                                .flatMap(user -> Optional.ofNullable(user.getAvatar()))
                                .orElse(DefaultUsersAvatar.DEFAULT_AVATAR);
        return Base64.getDecoder().decode(avatar);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public void changePassword(String id, String password) {
        User user = tryToGetUserById(id).get();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }
}