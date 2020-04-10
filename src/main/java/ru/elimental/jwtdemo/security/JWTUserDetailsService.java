package ru.elimental.jwtdemo.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.elimental.jwtdemo.model.User;
import ru.elimental.jwtdemo.security.jwt.JWTUser;
import ru.elimental.jwtdemo.security.jwt.JWTUserFactory;
import ru.elimental.jwtdemo.service.UserService;

@Service
@Slf4j
@Qualifier(value = "JWT")
public class JWTUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public JWTUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userService.findByUserName(userName);
        if (user == null) {
            throw new UsernameNotFoundException("User with username: " + userName + "not found");
        }
        JWTUser jwtUser = JWTUserFactory.create(user);
        log.info("IN loadUserByUsername - user with username: {} successfully loaded", userName);
        return jwtUser;
    }
}
