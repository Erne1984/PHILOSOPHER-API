package com.floriano.philosophy_api.services.QuoteService.utils;

import com.floriano.philosophy_api.dto.QuoteDTO.QuoteRequestDTO;
import com.floriano.philosophy_api.exceptions.PhilosopherIdNotFoundException;
import com.floriano.philosophy_api.exceptions.WorkIdNotFoundException;
import com.floriano.philosophy_api.model.Philosopher.Philosopher;
import com.floriano.philosophy_api.model.Quote.Quote;
import com.floriano.philosophy_api.model.Theme.Theme;
import com.floriano.philosophy_api.model.Work.Work;
import com.floriano.philosophy_api.repositories.PhilosopherRepository.PhilosopherRepository;
import com.floriano.philosophy_api.repositories.ThemeRepository.ThemeRepository;
import com.floriano.philosophy_api.repositories.WorkRepository.WorkRepository;

import java.util.List;

public class QuoteUpdateHelper {

    public static void updateBasicFields(Quote quote, QuoteRequestDTO dto) {
        if (dto.getContent() != null && !dto.getContent().trim().isEmpty()) {
            quote.setContent(dto.getContent().trim());
        }
    }

    public static void updatePhilosopher(Quote quote, QuoteRequestDTO dto, PhilosopherRepository philosopherRepository) {
        if (dto.getPhilosopherId() != null) {
            Philosopher p = philosopherRepository.findById(dto.getPhilosopherId())
                    .orElseThrow(() -> new PhilosopherIdNotFoundException("Philosopher not found"));
            quote.setPhilosopher(p);
        }
    }

    public static void updateWork(Quote quote, QuoteRequestDTO dto, WorkRepository workRepository) {
        if (dto.getWorkId() != null) {
            Work w = workRepository.findById(dto.getWorkId())
                    .orElseThrow(() -> new WorkIdNotFoundException("Work not found"));
            quote.setWork(w);
        }
    }

    public static void updateThemes(Quote quote, List<Long> themesId, ThemeRepository themeRepository) {
        if (themesId == null) return;

        List<Theme> newThemes = themeRepository.findAllById(themesId);
        if (newThemes.size() != themesId.size()) {
            throw new IllegalArgumentException("Um ou mais temas não foram encontrados.");
        }

        quote.clearThemes();
        for (Theme t : newThemes) {
            quote.addTheme(t);
        }
    }

}
