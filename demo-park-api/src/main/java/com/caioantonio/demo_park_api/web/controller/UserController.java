package com.caioantonio.demo_park_api.web.controller;

import com.caioantonio.demo_park_api.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor // adiciona a variavél userService no constructor e realizar a injeção de dependências
public class UserController {

    private final UserService userService;


}
