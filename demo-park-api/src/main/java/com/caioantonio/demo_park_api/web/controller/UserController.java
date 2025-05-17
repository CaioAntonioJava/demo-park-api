package com.caioantonio.demo_park_api.web.controller;

import com.caioantonio.demo_park_api.entity.User;
import com.caioantonio.demo_park_api.service.UserService;
import com.caioantonio.demo_park_api.web.dto.UserChangePasswordDto;
import com.caioantonio.demo_park_api.web.dto.UserCreateDto;
import com.caioantonio.demo_park_api.web.dto.UserResponseDto;
import com.caioantonio.demo_park_api.web.dto.mapper.UserMapper;
import com.caioantonio.demo_park_api.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Usuários", description = "Contém todas as operações relativas aos recursos para cadastro, edição e leitura de um usuário")
@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "Criar um novo usuário", description = "Recurso para criar um novo usuário",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "409", description = "Usuário e-mail já cadastrado no sistema",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Recurso não processado por dados de entrada inválidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PostMapping
    public ResponseEntity<UserResponseDto> create(@Valid @RequestBody UserCreateDto createDto) {
        User newUser = userService.save(UserMapper.toUser(createDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toDto(newUser));

    }

    @Operation(
            summary = "Recuperar um usuário pelo id", description = "Recuperar um usuário pelo id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso recuperado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
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

