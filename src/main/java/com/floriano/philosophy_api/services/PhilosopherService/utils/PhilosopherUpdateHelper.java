package com.floriano.philosophy_api.services.PhilosopherService.utils;

import com.floriano.philosophy_api.dto.PhilosopherDTO.PhilosopherRequestDTO;
import com.floriano.philosophy_api.exceptions.CountryNotFoundException;
import com.floriano.philosophy_api.exceptions.QuoteIdNotFoundException;
import com.floriano.philosophy_api.model.Country.Country;
import com.floriano.philosophy_api.model.Philosopher.Philosopher;
import com.floriano.philosophy_api.model.Quote.Quote;
import com.floriano.philosophy_api.model.SchoolOfThought.SchoolOfThought;
import com.floriano.philosophy_api.model.Theme.Theme;
import com.floriano.philosophy_api.repositories.CountryRepository.CountryRepository;
import com.floriano.philosophy_api.repositories.QuoteRepository.QuoteRepository;
import com.floriano.philosophy_api.repositories.SchoolOfThoughtRepository.SchoolOfThoughtRepository;
import com.floriano.philosophy_api.repositories.ThemeRepository.ThemeRepository;

import java.util.List;

public class PhilosopherUpdateHelper {

    public static void updateBasicFields(Philosopher philosopher, PhilosopherRequestDTO dto) {
        if (dto.getName() != null && !dto.getName().trim().isEmpty()) {
            philosopher.setName(dto.getName().trim());
        }
        philosopher.setBirthYear(dto.getBirthYear());
        philosopher.setDeathYear(dto.getDeathYear() != null ? dto.getDeathYear() : 0);
        if (dto.getBio() != null) {
            philosopher.setBio(dto.getBio().trim());
        }
    }

    public static void updateCountry(Philosopher philosopher, PhilosopherRequestDTO dto, CountryRepository countryRepository) {
        if (dto.getCountryId() != null) {
            Country country = countryRepository.findById(dto.getCountryId())
                    .orElseThrow(() -> new CountryNotFoundException("País não encontrado"));
            philosopher.setCountry(country);
        }
    }

    public static void updateQuotes(Philosopher philosopher, List<Long> quotesId, QuoteRepository quoteRepository) {
        if (quotesId == null) return;

        List<Quote> quotes = quoteRepository.findAllById(quotesId);
        if (quotes.size() != quotesId.size()) {
            throw new QuoteIdNotFoundException("Uma ou mais citações não foram encontradas.");
        }

        philosopher.getQuotes().clear();
        philosopher.getQuotes().addAll(quotes);
    }

    public static void updateSchoolOfThoughts(Philosopher philosopher, List<Long> schoolIds, SchoolOfThoughtRepository schoolRepository) {
        if (schoolIds == null) return;

        List<SchoolOfThought> schools = schoolRepository.findAllById(schoolIds);
        if (schools.size() != schoolIds.size()) {
            throw new IllegalArgumentException("Uma ou mais escolas de pensamento não foram encontradas.");
        }

        philosopher.clearSchoolOfThought();
        for (SchoolOfThought s : schools) {
            philosopher.addSchoolOfThought(s);
        }
    }

    public static void updateThemes(Philosopher philosopher, List<Long> themeIds, ThemeRepository themeRepository) {
        if (themeIds == null) return;

        List<Theme> themes = themeRepository.findAllById(themeIds);
        if (themes.size() != themeIds.size()) {
            throw new IllegalArgumentException("Um ou mais temas não foram encontrados.");
        }

        philosopher.clearThemes();
        for (Theme t : themes) {
            philosopher.addTheme(t);
        }
    }
}