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

    private String name;
    private String isoCode;
    private Integer startYear;
    private Integer endYear;
    private String region;

    private List<String> philosophers;
    private List<String> works;
}
