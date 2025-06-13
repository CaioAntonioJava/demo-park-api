package com.caioantonio.demo_park_api.web.controller;

import com.caioantonio.demo_park_api.entity.Client;
import com.caioantonio.demo_park_api.jwt.JwtUserDetails;
import com.caioantonio.demo_park_api.repository.projection.ClientProjection;
import com.caioantonio.demo_park_api.service.ClientService;
import com.caioantonio.demo_park_api.service.UserService;
import com.caioantonio.demo_park_api.web.dto.ClientCreateDto;
import com.caioantonio.demo_park_api.web.dto.ClientResponseDto;
import com.caioantonio.demo_park_api.web.dto.PageableDto;
import com.caioantonio.demo_park_api.web.dto.mapper.ClientMapper;
import com.caioantonio.demo_park_api.web.dto.mapper.PageableMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClientResponseDto> getById(@PathVariable Long id) {
        Client client = clientService.getById(id);
        return ResponseEntity.ok().body(ClientMapper.toResponseDto(client));
    }

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PageableDto> getAllClients(Pageable pageable) {
        Page<ClientProjection> clients = clientService.getAllClientes(pageable);
        return ResponseEntity.ok(PageableMapper.toPageDto(clients));
    }
}
