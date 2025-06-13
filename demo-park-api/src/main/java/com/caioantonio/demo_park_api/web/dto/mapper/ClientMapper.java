package com.caioantonio.demo_park_api.web.dto.mapper;

import com.caioantonio.demo_park_api.entity.Client;
import com.caioantonio.demo_park_api.entity.User;
import com.caioantonio.demo_park_api.web.dto.ClientCreateDto;
import com.caioantonio.demo_park_api.web.dto.ClientResponseDto;
import com.caioantonio.demo_park_api.web.dto.UserResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)

public class ClientMapper {

    public static Client toClient(ClientCreateDto clientCreateDto) {
        return new ModelMapper().map(clientCreateDto, Client.class);
    }

    public static ClientResponseDto toResponseDto(Client client) {
        return new ModelMapper().map(client, ClientResponseDto.class);
    }


}
