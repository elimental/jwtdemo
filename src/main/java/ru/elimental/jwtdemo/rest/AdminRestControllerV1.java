package ru.elimental.jwtdemo.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.elimental.jwtdemo.dto.UserDto;
import ru.elimental.jwtdemo.model.User;
import ru.elimental.jwtdemo.service.UserService;

import java.util.UUID;

@RestController
@RequestMapping(value = AdminRestControllerV1.REST_URL)
public class AdminRestControllerV1 {

    final static String REST_URL = "/api/v1/admin/";

    private final UserService userService;

    @Autowired
    public AdminRestControllerV1(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "users/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable(name = "id") UUID id) {
        User user = userService.findById(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        UserDto userDto = UserDto.fromUser(user);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
}
