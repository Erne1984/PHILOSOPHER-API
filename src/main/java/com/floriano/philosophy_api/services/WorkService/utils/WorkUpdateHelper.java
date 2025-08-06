package com.floriano.philosophy_api.services.WorkService.utils;

import com.floriano.philosophy_api.dto.WorkDTO.WorkRequestDTO;
import com.floriano.philosophy_api.exceptions.CountryNotFoundException;
import com.floriano.philosophy_api.exceptions.PhilosopherIdNotFoundException;
import com.floriano.philosophy_api.exceptions.SchoolOfThoghtNotFoundException;
import com.floriano.philosophy_api.exceptions.ThemeIdNotFoundException;
import com.floriano.philosophy_api.model.Country.Country;
import com.floriano.philosophy_api.model.Philosopher.Philosopher;
import com.floriano.philosophy_api.model.SchoolOfThought.SchoolOfThought;
import com.floriano.philosophy_api.model.Theme.Theme;
import com.floriano.philosophy_api.model.Work.Work;
import com.floriano.philosophy_api.repositories.CountryRepository.CountryRepository;
import com.floriano.philosophy_api.repositories.PhilosopherRepository.PhilosopherRepository;
import com.floriano.philosophy_api.repositories.SchoolOfThoughtRepository.SchoolOfThoughtRepository;
import com.floriano.philosophy_api.repositories.ThemeRepository.ThemeRepository;

import java.util.List;

public class WorkUpdateHelper {

    public static void updateBasicFields(Work work, WorkRequestDTO dto) {

        if (dto.getTitle() != null && !dto.getTitle().isEmpty()) {
            work.setTitle(dto.getTitle().trim());
        }

        if (dto.getYear() != null) {
            work.setYear(dto.getYear());
        }

        if (dto.getSummary() != null && !dto.getSummary().isEmpty()) {
            work.setSummary(dto.getSummary().trim());
        }
    }

    public static void updatePhilosopher(Work work, WorkRequestDTO dto, PhilosopherRepository philosopherRepository) {
        if (dto.getPhilosopherId() != null) {
            Philosopher p = philosopherRepository.findById(dto.getPhilosopherId())
                    .orElseThrow(() -> new PhilosopherIdNotFoundException("Philosopher not found"));
            work.setPhilosopher(p);
        }
    }

    public static void updateCountry(Work work, WorkRequestDTO dto, CountryRepository countryRepository) {
        if (dto.getCountryId() != null) {
            Country c = countryRepository.findById(dto.getCountryId())
                    .orElseThrow(() -> new CountryNotFoundException("Country not found"));
            work.setCountry(c);
        }
    }

    public static void updateSchoolOfThoughts(Work work, List<Long> schoolOfThoughtsIds, SchoolOfThoughtRepository schoolOfThoughtRepository) {
        if (schoolOfThoughtsIds != null) {
            List<SchoolOfThought> newSchoolOfThoughts = schoolOfThoughtRepository.findAllById(schoolOfThoughtsIds);
            if (newSchoolOfThoughts.size() != schoolOfThoughtsIds.size()) {
                throw new SchoolOfThoghtNotFoundException("One or More Schools not found!");
            }

            work.clearSchoolOfThoughts();
            for (SchoolOfThought s : newSchoolOfThoughts) {
                work.addSchoolOfThought(s);
            }
        }
    }

    public static void updateThemes(Work work, List<Long> themesId, ThemeRepository themeRepository) {
        if (themesId != null) {
            List<Theme> newThemes = themeRepository.findAllById(themesId);
            if (newThemes.size() != themesId.size()) {
                throw new ThemeIdNotFoundException("One or more Themes not found!");
            }

            work.clearThemes();
            for (Theme t : newThemes) {
                work.addTheme(t);
            }
        }
    }

}
