package com.caioantonio.demo_park_api.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PageableDto {
    private List content = new ArrayList<>();

    private boolean first;

    private boolean last;

    @JsonProperty("page") // alterar o nome no doc.json de number p/ page
    private int number;

    private int size;

    @JsonProperty("pageElements") // alterar o nome no doc.json de numberOfElements p/ pageElements
    private int numberOfElements;

    private int totalPages;

    private int totalElements;
}
