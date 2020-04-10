package ru.elimental.jwtdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.elimental.jwtdemo.model.Role;

import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {

    Role findByName(String name);
}
