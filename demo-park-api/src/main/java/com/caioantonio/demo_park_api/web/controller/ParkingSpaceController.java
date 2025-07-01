package com.caioantonio.demo_park_api.web.controller;

import com.caioantonio.demo_park_api.entity.ParkingSpace;
import com.caioantonio.demo_park_api.service.ParkingSpaceService;
import com.caioantonio.demo_park_api.web.dto.ParkingSpaceCreateDto;
import com.caioantonio.demo_park_api.web.dto.ParkingSpaceResponseDto;
import com.caioantonio.demo_park_api.web.dto.mapper.ParkingSpaceMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/parkingSpaces")
public class ParkingSpaceController {

    private final ParkingSpaceService parkingSpaceService;

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
