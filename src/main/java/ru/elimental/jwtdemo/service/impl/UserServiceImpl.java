package ru.elimental.jwtdemo.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.elimental.jwtdemo.model.Role;
import ru.elimental.jwtdemo.model.User;
import ru.elimental.jwtdemo.repository.RoleRepository;
import ru.elimental.jwtdemo.repository.UserRepository;
import ru.elimental.jwtdemo.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hibernate.cfg.AvailableSettings.USER;
import static ru.elimental.jwtdemo.model.Status.ACTIVE;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(User user) {
        Role roleUser = roleRepository.findByName(USER);
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);
        user.setStatus(ACTIVE);
        User registeredUser = userRepository.save(user);
        log.info("IN register = user: {} successfully registered", registeredUser);
        return registeredUser;
    }

    @Override
    public List<User> getAll() {
        List<User> users = userRepository.findAll();
        log.info("IN getAll - {} users found", users.size());
        return users;
    }

    @Override
    public User findByUserName(String userName) {
        User user = userRepository.findByUsername(userName);
        log.info("IN findByUsername - user: {} found by username: {}", user, userName);
        return user;
    }

    @Override
    public User findById(UUID id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            log.warn("IN findById - user with id {} was not found", id);
            return null;
        }
        log.info("IN findById - user: {} found by id: {}", user, id);
        return user;
    }

    @Override
    public void delete(UUID id) {
        userRepository.deleteById(id);
        log.info("IN delete - user with id: {} successfully deleted", id);
    }
}
