package com.renaudk.user_service.controller;


import com.renaudk.user_service.entity.User;
import com.renaudk.user_service.entity.UserDto;
import com.renaudk.user_service.service.UserService;
import com.renaudk.user_service.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserServiceController {

    private final UserService userService;
    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody UserDto userDto) {
        User user = userService.create(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }


    @PatchMapping("/{userId}")
    public ResponseEntity<User> update(@PathVariable("userId") Long userId, @RequestBody UserDto userDto) {
        User updatedUser = userService.updateUser(userId, userDto);
        return ResponseEntity.ok(updatedUser);
    }

}
