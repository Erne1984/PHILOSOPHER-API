package com.floriano.philosophy_api.services.ThemeService.utils;

import com.floriano.philosophy_api.dto.ThemeDTO.ThemeRequestDTO;
import com.floriano.philosophy_api.exceptions.PhilosopherIdNotFoundException;
import com.floriano.philosophy_api.exceptions.QuoteIdNotFoundException;
import com.floriano.philosophy_api.exceptions.WorkIdNotFoundException;
import com.floriano.philosophy_api.model.Philosopher.Philosopher;
import com.floriano.philosophy_api.model.Quote.Quote;
import com.floriano.philosophy_api.model.Theme.Theme;
import com.floriano.philosophy_api.model.Work.Work;
import com.floriano.philosophy_api.repositories.PhilosopherRepository.PhilosopherRepository;
import com.floriano.philosophy_api.repositories.QuoteRepository.QuoteRepository;
import com.floriano.philosophy_api.repositories.WorkRepository.WorkRepository;

import java.util.List;

public class ThemeUpdateHelper {

    public static void updateBasicFields(Theme theme, ThemeRequestDTO dto) {
        if (dto.getName() != null && !dto.getName().trim().isEmpty()) {
            theme.setName(dto.getName().trim());
        }
        if (dto.getDescription() != null && !dto.getDescription().trim().isEmpty()) {
            theme.setDescription(dto.getDescription().trim());
        }
    }

    public static void updatePhilosophers(Theme theme, List<Long> philosopherIds, PhilosopherRepository philosopherRepository) {
        if (philosopherIds == null) return;

        theme.clearPhilosophers();
        List<Philosopher> newPhilosophers = philosopherRepository.findAllById(philosopherIds);
        if (newPhilosophers.size() != philosopherIds.size()) {
            throw new PhilosopherIdNotFoundException("One or more philosophers not found");
        }
        for (Philosopher p : newPhilosophers) {
            theme.addPhilosopher(p);
        }
    }

    public static void updateWorks(Theme theme, List<Long> workIds, WorkRepository workRepository) {
        if (workIds == null) return;

        theme.clearWorks();
        List<Work> newWorks = workRepository.findAllById(workIds);
        if (newWorks.size() != workIds.size()) {
            throw new WorkIdNotFoundException("One or more works not found");
        }
        for (Work w : newWorks) {
            theme.addWork(w);
        }
    }

    public static void updateQuotes(Theme theme, List<Long> quoteIds, QuoteRepository quoteRepository) {
        if (quoteIds == null) return;

        theme.clearQuotes();
        List<Quote> newQuotes = quoteRepository.findAllById(quoteIds);
        if (newQuotes.size() != quoteIds.size()) {
            throw new QuoteIdNotFoundException("One or more quotes not found");
        }
        for (Quote q : newQuotes) {
            theme.addQuote(q);
        }
    }
}
