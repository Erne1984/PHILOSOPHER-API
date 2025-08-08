package com.floriano.philosophy_api.mapper;

import com.floriano.philosophy_api.dto.SchoolOfThoughtDTO.SchoolOfThoughtRequestDTO;
import com.floriano.philosophy_api.dto.SchoolOfThoughtDTO.SchoolOfThoughtResponseDTO;
import com.floriano.philosophy_api.model.Philosopher.Philosopher;
import com.floriano.philosophy_api.model.Quote.Quote;
import com.floriano.philosophy_api.model.SchoolOfThought.SchoolOfThought;
import com.floriano.philosophy_api.model.Work.Work;

import java.util.List;
import java.util.stream.Collectors;

public class SchoolOfThoughtMapper {

    public static SchoolOfThought toEntity(SchoolOfThoughtRequestDTO dto, List<Work> works, List<Philosopher> philosophers) {
        SchoolOfThought schoolOfThought = new SchoolOfThought();

        schoolOfThought.setName(dto.getName());
        schoolOfThought.setDescription(dto.getDescription());
        schoolOfThought.setOriginCentury(dto.getOriginCentury());

        for (Philosopher philosopher : philosophers) {
            schoolOfThought.addPhilosopher(philosopher);
        }

        for (Work work : works) {
            schoolOfThought.addWork(work);
        }

        schoolOfThought.setWorks(works);
        schoolOfThought.setPhilosophers(philosophers);

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
