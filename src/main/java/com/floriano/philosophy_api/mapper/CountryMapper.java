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
                country.getName(),
                country.getStartYear(),
                country.getEndYear() != null ? country.getEndYear() : null,
                country.getRegion() != null ? country.getRegion() : null,
                country.getPhilosophers() != null ? country.getPhilosophers()
                        .stream()
                        .map((philosopher -> philosopher.getName()))
                        .toList()
                        :
                        null,
                country.getWorks() != null ? country.getWorks()
                        .stream()
                        .map((work -> work.getTitle()))
                        .toList()
                        :
                        null
        );
    }
}
