package com.caioantonio.demo_park_api.web.dto.mapper;

import com.caioantonio.demo_park_api.entity.ParkingSpace;
import com.caioantonio.demo_park_api.web.dto.ParkingSpaceCreateDto;
import com.caioantonio.demo_park_api.web.dto.ParkingSpaceResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ParkingSpaceMapper {

    public static ParkingSpace toParkingSpace(ParkingSpaceCreateDto parkingSpaceCreateDto) {
        return new ModelMapper().map(parkingSpaceCreateDto, ParkingSpace.class);
    }

    public static ParkingSpaceResponseDto toParkingSpaceResponseDto (ParkingSpace parkingSpace) {
        return new ModelMapper().map(parkingSpace, ParkingSpaceResponseDto.class);
    }
}
