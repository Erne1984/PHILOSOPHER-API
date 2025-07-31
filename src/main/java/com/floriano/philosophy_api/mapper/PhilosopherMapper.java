package com.floriano.philosophy_api.mapper;

import com.floriano.philosophy_api.dto.PhilosopherDTO.PhilosopherRequestDTO;
import com.floriano.philosophy_api.dto.PhilosopherDTO.PhilosopherResponseDTO;
import com.floriano.philosophy_api.model.Country.Country;
import com.floriano.philosophy_api.model.Philosopher.Philosopher;

import java.util.List;
import java.util.stream.Collectors;

public class PhilosopherMapper {

    public static Philosopher toEntity(PhilosopherRequestDTO dto, Country country) {
        Philosopher philosopher = new Philosopher();

        philosopher.setName(dto.getName());
        philosopher.setBirthYear(dto.getBirthYear());
        philosopher.setDeathYear(dto.getDeathYear());
        philosopher.setBio(dto.getBio());
        philosopher.setCountry(country);

        return philosopher;
    }

    public static PhilosopherResponseDTO toDTO(Philosopher philosopher) {
        return new PhilosopherResponseDTO(
                philosopher.getId(),
                philosopher.getName(),
                philosopher.getBirthYear(),
                philosopher.getDeathYear(),
                philosopher.getBio(),
                philosopher.getCountry().getName(),
                philosopher.getQuotes() != null
                        ? philosopher.getQuotes().stream().map(quote -> quote.getContent()).collect(Collectors.toList())
                        : List.of()
        );
    }
}
