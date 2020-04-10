package ru.elimental.jwtdemo.security.jwt;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.elimental.jwtdemo.model.Role;
import ru.elimental.jwtdemo.model.Status;
import ru.elimental.jwtdemo.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JWTUserFactory {

    public JWTUserFactory() {
    }

    public static JWTUser create(User user) {
        JWTUser jwtUser = new JWTUser(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getPassword(),
                user.getEmail(),
                user.getStatus().equals(Status.ACTIVE),
                user.getUpdated(),
                mapToGrantedAuthorities(new ArrayList<>(user.getRoles()))
        );
        return jwtUser;
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> userRoles) {
        return userRoles.stream()
                .map(role ->
                        new SimpleGrantedAuthority(role.getName())
                ).collect(Collectors.toList());
    }
}
