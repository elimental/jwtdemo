package ru.elimental.jwtdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.elimental.jwtdemo.model.User;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    User findByUsername(String name);
}
