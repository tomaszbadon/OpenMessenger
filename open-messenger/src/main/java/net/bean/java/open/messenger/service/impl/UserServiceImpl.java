package net.bean.java.open.messenger.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import net.bean.java.open.messenger.model.jpa.User;
import net.bean.java.open.messenger.model.jpa.Role;
import net.bean.java.open.messenger.repository.RoleRepository;
import net.bean.java.open.messenger.repository.UserRepository;
import net.bean.java.open.messenger.service.UserService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j @RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = Optional.ofNullable(userRepository.findByUserName(username)).orElseThrow(() -> {
            log.error("User '{}' not found in the database", username);
            return new UsernameNotFoundException("User not found in the database: " + username);
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
    public Role saveRole(Role role) {
        log.info("Save a Role to the database", role.getName());
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String userName, String roleName) {
        User user = userRepository.findByUserName(userName);
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
    }

    @Override
    public Optional<User> getUser(String userName) {
        return Optional.ofNullable(userRepository.findByUserName(userName));
    }

    @Override
    public Optional<User> getUser(Long id) {
        return Optional.ofNullable(userRepository.getById(id));
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }
}