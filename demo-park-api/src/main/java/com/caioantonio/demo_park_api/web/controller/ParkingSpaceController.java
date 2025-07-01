package com.caioantonio.demo_park_api.web.controller;

import com.caioantonio.demo_park_api.entity.ParkingSpace;
import com.caioantonio.demo_park_api.service.ParkingSpaceService;
import com.caioantonio.demo_park_api.web.dto.ClientResponseDto;
import com.caioantonio.demo_park_api.web.dto.ParkingSpaceCreateDto;
import com.caioantonio.demo_park_api.web.dto.ParkingSpaceResponseDto;
import com.caioantonio.demo_park_api.web.dto.mapper.ParkingSpaceMapper;
import com.caioantonio.demo_park_api.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
@Tag(name = "Vagas de Estacionamento", description = "Contém todas as operações relativas aos recursos para cadastro e leitura de uma vaga")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/parkingSpaces")
public class ParkingSpaceController {

    private final ParkingSpaceService parkingSpaceService;

    @Operation(
            summary = "Criar uma nova vaga de estacionamento", description = "Recurso para criar uma nova vaga de estacionamento. " +
            "Requisição exige uso de um bearer token. Acesso restrito a ADMIN",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClientResponseDto.class))),
                    @ApiResponse(responseCode = "409", description = "Código da vaga já possuí cadastro no sistema",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Recurso não processado por dados de entrada inválidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Recurso não permitido ao perfil de CLIENTE",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ParkingSpaceResponseDto> create(@RequestBody @Valid ParkingSpaceCreateDto parkingSpaceCreateDto) {

        ParkingSpace parkingSpace = ParkingSpaceMapper.toParkingSpace(parkingSpaceCreateDto);
        parkingSpaceService.create(parkingSpace);

//        URI location = ServletUriComponentsBuilder
//                .fromCurrentRequestUri().path("/{code}")
//                .buildAndExpand(parkingSpace.getCode())
//                .toUri();

        return ResponseEntity.status(HttpStatus.CREATED).body(ParkingSpaceMapper.toParkingSpaceResponseDto(parkingSpace));
    }

    @GetMapping("/{code}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ParkingSpaceResponseDto> getByCode(@PathVariable String code) {

        ParkingSpace parkingSpace = parkingSpaceService.getByCode(code);

        return ResponseEntity.ok(ParkingSpaceMapper.toParkingSpaceResponseDto(parkingSpace));
    }
}
