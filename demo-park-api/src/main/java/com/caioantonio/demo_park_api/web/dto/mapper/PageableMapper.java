package com.caioantonio.demo_park_api.web.dto.mapper;

import com.caioantonio.demo_park_api.web.dto.PageableDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PageableMapper {

    public static PageableDto toPageDto(Page page) {
        return new ModelMapper().map(page,PageableDto.class );
    }
}
