package com.floriano.philosophy_api.mapper;

import com.floriano.philosophy_api.dto.SchoolOfThoughtDTO.SchoolOfThoughtRequestDTO;
import com.floriano.philosophy_api.dto.SchoolOfThoughtDTO.SchoolOfThoughtResponseDTO;
import com.floriano.philosophy_api.model.SchoolOfThought.SchoolOfThought;

import java.util.stream.Collectors;

public class SchoolOfThoughtMapper {

    public static SchoolOfThought toEntity(SchoolOfThoughtRequestDTO dto) {
        SchoolOfThought schoolOfThought = new SchoolOfThought();

        schoolOfThought.setName(dto.getName());
        schoolOfThought.setDescription(dto.getDescription());
        schoolOfThought.setOriginCentury(dto.getOriginCentury());

        // IMPLEMENTAR POSTERIORMENTE

        schoolOfThought.setWorks(null);
        schoolOfThought.setPhilosophers(null);

        return schoolOfThought;
    }

    public static SchoolOfThoughtResponseDTO toDTO(SchoolOfThought schoolOfThought) {
        return new SchoolOfThoughtResponseDTO(
                schoolOfThought.getId(),
                schoolOfThought.getName(),
                schoolOfThought.getDescription(),
                schoolOfThought.getOriginCentury(),
                schoolOfThought.getPhilosophers() != null ?
                        schoolOfThought.getPhilosophers()
                                .stream()
                                .map(philosopher -> philosopher.getName())
                                .collect(Collectors.toList()) :
                        null,
                schoolOfThought.getWorks() != null ?
                        schoolOfThought.getWorks()
                                .stream()
                                .map(work -> work.getTitle())
                                .collect(Collectors.toList()) :
                                null
        );
    }
}
