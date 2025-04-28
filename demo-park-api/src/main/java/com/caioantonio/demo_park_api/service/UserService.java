package com.caioantonio.demo_park_api.service;

import com.caioantonio.demo_park_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // adiciona a variavél userRepository no constructor e realizar a injeção de dependências
public class UserService {
    private final UserRepository userRepository;
}
