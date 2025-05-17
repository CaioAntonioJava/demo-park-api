package com.caioantonio.demo_park_api.web.controller;

import com.caioantonio.demo_park_api.entity.User;
import com.caioantonio.demo_park_api.service.UserService;
import com.caioantonio.demo_park_api.web.dto.UserChangePasswordDto;
import com.caioantonio.demo_park_api.web.dto.UserCreateDto;
import com.caioantonio.demo_park_api.web.dto.UserResponseDto;
import com.caioantonio.demo_park_api.web.dto.mapper.UserMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDto> create(@Valid @RequestBody UserCreateDto createDto) {
        User newUser = userService.save(UserMapper.toUser(createDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toDto(newUser));

    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getById(@PathVariable Long id) {
        User user = userService.findById(id);
        return ResponseEntity.ok().body(UserMapper.toDto(user));
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        List<User> userList = userService.findAllUsers();
        return ResponseEntity.ok(UserMapper.toListDto(userList));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id, @Valid @RequestBody UserChangePasswordDto userChangePasswordDto) {
        User userObj = userService.changeUserPassword(
                id,
                userChangePasswordDto.getNewPassword(),
                userChangePasswordDto.getConfirmPassword(),
                userChangePasswordDto.getCurrentPassword());
        return ResponseEntity.noContent().build();
    }
}

