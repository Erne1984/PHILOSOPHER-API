package com.floriano.philosophy_api.dto.CountryDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CountryRequestDTO {

    private String name;
    private Integer startYear;
    private Integer endYear;
    private String region;


}
