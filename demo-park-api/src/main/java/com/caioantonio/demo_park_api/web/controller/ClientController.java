package com.caioantonio.demo_park_api.web.controller;

import com.caioantonio.demo_park_api.entity.Client;
import com.caioantonio.demo_park_api.jwt.JwtUserDetails;
import com.caioantonio.demo_park_api.service.ClientService;
import com.caioantonio.demo_park_api.service.UserService;
import com.caioantonio.demo_park_api.web.dto.ClientCreateDto;
import com.caioantonio.demo_park_api.web.dto.ClientResponseDto;
import com.caioantonio.demo_park_api.web.dto.mapper.ClientMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;
    private final UserService userService;

    @PostMapping
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<ClientResponseDto> create(
            @RequestBody @Valid ClientCreateDto clientCreateDto, @AuthenticationPrincipal JwtUserDetails userDetails) {
        Client client = ClientMapper.toClient(clientCreateDto);
        client.setUser(userService.findById(userDetails.getId()));
        clientService.create(client);
        return ResponseEntity.status(201).body(ClientMapper.toResponseDto(client));
    }
}
