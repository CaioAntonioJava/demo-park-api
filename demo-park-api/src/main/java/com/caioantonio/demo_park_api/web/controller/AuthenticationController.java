package com.caioantonio.demo_park_api.web.controller;

import com.caioantonio.demo_park_api.jwt.JwtToken;
import com.caioantonio.demo_park_api.jwt.JwtUserDetailsService;
import com.caioantonio.demo_park_api.web.dto.UserLoginDto;
import com.caioantonio.demo_park_api.web.exception.ErrorMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/api/v1")
@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final JwtUserDetailsService jwtUserDetailsService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/auth")
    public ResponseEntity<?> authenticate(@RequestBody @Valid UserLoginDto loginDto, HttpServletRequest request) {

        log.info("Processo de autenticação pelo login { %s } ", loginDto.getUsername());

        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

            authenticationManager.authenticate(authenticationToken);

            JwtToken token = jwtUserDetailsService.getTokenAuthenticated(loginDto.getUsername());

            return ResponseEntity.ok(token);

        } catch (AuthenticationException authenticationException) {

            log.warn("Bad Credentials from username '{%s}'", loginDto.getUsername());
        }
        return ResponseEntity
                .badRequest().body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, "Credenciais inválidas"));
    }
}
