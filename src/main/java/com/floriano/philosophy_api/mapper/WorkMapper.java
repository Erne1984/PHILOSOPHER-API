package com.floriano.philosophy_api.mapper;

import com.floriano.philosophy_api.dto.WorkDTO.WorkRequestDTO;
import com.floriano.philosophy_api.dto.WorkDTO.WorkResponseDTO;
import com.floriano.philosophy_api.model.Country.Country;
import com.floriano.philosophy_api.model.Philosopher.Philosopher;
import com.floriano.philosophy_api.model.SchoolOfThought.SchoolOfThought;
import com.floriano.philosophy_api.model.Theme.Theme;
import com.floriano.philosophy_api.model.Work.Work;

import java.util.List;
import java.util.stream.Collectors;

public class WorkMapper {

    public static Work toEntity(WorkRequestDTO dto, Philosopher philosopher, Country country) {
        Work work = new Work();
        work.setTitle(dto.getTitle());
        work.setYear(dto.getYear());
        work.setSummary(dto.getSummary());
        work.setPhilosopher(philosopher);
        work.setCountry(country);

        // Relacionamentos deixados como null por enquanto
        work.setSchoolOfThoughts(null);
        work.setThemes(null);

        return work;
    }


    public static WorkResponseDTO toDTO(Work work) {
        return new WorkResponseDTO(
                work.getId(),
                work.getTitle(),
                work.getYear(),
                work.getSummary(),
                work.getPhilosopher() != null ? work.getPhilosopher().getName() : null,
                work.getCountry() != null ? work.getCountry().getName() : null,
                work.getSchoolOfThoughts() != null
                        ? work.getSchoolOfThoughts().stream()
                        .map(school -> school.getName())
                        .collect(Collectors.toList())
                        : null,
                work.getThemes() != null
                        ? work.getThemes().stream()
                        .map(theme -> theme.getName())
                        .collect(Collectors.toList())
                        : null
        );
    }
}
