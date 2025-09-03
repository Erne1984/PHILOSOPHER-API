package com.floriano.philosophy_api.services.CountryService.utils;

import com.floriano.philosophy_api.dto.CountryDTO.CountryRequestDTO;
import com.floriano.philosophy_api.model.Country.Country;

public class CountryUpdateHelper {

    public static void updateBasicFields(Country country, CountryRequestDTO dto) {

        if (dto.getName() != null) {
            country.setName(dto.getName());
        }

        if (dto.getStartYear() != null) {
            country.setStartYear(dto.getStartYear());
        }

        if (dto.getEndYear() != null || dto.getEndYear() == null) {
            country.setEndYear(dto.getEndYear());
        }

        if (dto.getRegion() != null || dto.getRegion() == null) {
            country.setRegion(dto.getRegion());
        }
    }
}
