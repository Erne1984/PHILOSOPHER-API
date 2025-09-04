package com.floriano.philosophy_api.mapper;

import com.floriano.philosophy_api.dto.CountryDTO.CountryRequestDTO;
import com.floriano.philosophy_api.dto.CountryDTO.CountryResponseDTO;
import com.floriano.philosophy_api.model.Country.Country;

public class CountryMapper {

    public static Country toEntity(CountryRequestDTO dto) {

        Country country = new Country();

        country.setName(dto.getName());
        country.setStartYear(dto.getStartYear());
        country.setEndYear(dto.getEndYear());
        country.setRegion(dto.getRegion());

        return country;
    }

    public static CountryResponseDTO toDTO(Country country) {
        return new CountryResponseDTO(
                country.getId(),
                country.getName(),
                country.getStartYear(),
                country.getEndYear() != null ? country.getEndYear() : null,
                country.getRegion() != null ? country.getRegion() : null
        );
    }
}
