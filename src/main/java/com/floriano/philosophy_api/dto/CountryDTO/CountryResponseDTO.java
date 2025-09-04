package com.floriano.philosophy_api.dto.CountryDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CountryResponseDTO {

    private Long id;
    private String name;
    private Integer startYear;
    private Integer endYear;
    private String region;
}
