package com.floriano.philosophy_api.dto.SchoolOfThoughtDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SchoolOfThoughtRequestDTO {

    private String name;
    private String description;
    private int originCentury;
    private List<Long> philosophersIds;
    private List<Long> worksIds;
}
