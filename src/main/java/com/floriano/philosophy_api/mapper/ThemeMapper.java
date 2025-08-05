package com.floriano.philosophy_api.mapper;

import com.floriano.philosophy_api.dto.ThemeDTO.ThemeRequestDTO;
import com.floriano.philosophy_api.dto.ThemeDTO.ThemeResponseDTO;
import com.floriano.philosophy_api.model.Philosopher.Philosopher;
import com.floriano.philosophy_api.model.Quote.Quote;
import com.floriano.philosophy_api.model.Theme.Theme;
import com.floriano.philosophy_api.model.Work.Work;

import java.util.List;

public class ThemeMapper {

    public static Theme toEntity(ThemeRequestDTO dto, List<Philosopher> philosophers, List<Work> works, List<Quote> quotes) {
        Theme theme = new Theme();
        theme.setName(dto.getName());
        theme.setDescription(dto.getDescription());

        for (Philosopher philosopher : philosophers) {
            theme.addPhilosopher(philosopher);
        }

        for (Work work : works) {
            theme.addWork(work);
        }

        for (Quote quote : quotes) {
            theme.addQuote(quote);
        }

        return theme;
    }

    public static ThemeResponseDTO toDTO(Theme theme) {
        List<String> philosopherNames = theme.getPhilosophers()
                .stream()
                .map(Philosopher::getName)
                .toList();

        List<String> workTitles = theme.getWorks()
                .stream()
                .map(Work::getTitle)
                .toList();

        List<String> quoteContents = theme.getQuotes()
                .stream()
                .map(Quote::getContent)
                .toList();

        return new ThemeResponseDTO(
                theme.getId(),
                theme.getName(),
                theme.getDescription(),
                philosopherNames.isEmpty() ? null  : philosopherNames,
                workTitles.isEmpty() ? null :  workTitles,
                quoteContents.isEmpty() ? null  : quoteContents
        );
    }
}
