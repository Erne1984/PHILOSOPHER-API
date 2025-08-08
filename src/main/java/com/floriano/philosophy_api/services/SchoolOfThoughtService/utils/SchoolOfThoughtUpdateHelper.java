package com.floriano.philosophy_api.services.SchoolOfThoughtService.utils;

import com.floriano.philosophy_api.dto.SchoolOfThoughtDTO.SchoolOfThoughtRequestDTO;
import com.floriano.philosophy_api.model.Philosopher.Philosopher;
import com.floriano.philosophy_api.model.SchoolOfThought.SchoolOfThought;
import com.floriano.philosophy_api.model.Work.Work;

import java.util.List;

public class SchoolOfThoughtUpdateHelper {


    public static void updateBasicFields(SchoolOfThought school, SchoolOfThoughtRequestDTO dto) {
        if (dto.getName() != null && !dto.getName().isEmpty()) {
            school.setName(dto.getName().trim());
        }

        if (dto.getDescription() != null && !dto.getDescription().isEmpty()) {
            school.setDescription(dto.getDescription().trim());
        }

        if (dto.getOriginCentury() != 0) {
            school.setOriginCentury(dto.getOriginCentury());
        }
    }

    public static void updatePhilosophers(SchoolOfThought school, List<Philosopher> newPhilosophers) {
        if (newPhilosophers != null) {
            school.clearPhilosophers();
            for (Philosopher p : newPhilosophers) {
                school.addPhilosopher(p);
            }
        }
    }

    public static void updateWorks(SchoolOfThought school, List<Work> newWorks) {
        if (newWorks != null) {
            school.clearWorks();
            for (Work w : newWorks) {
                school.addWork(w);
            }
        }
    }
}
