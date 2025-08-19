package com.floriano.philosophy_api.mapper;

import com.floriano.philosophy_api.dto.QuoteDTO.QuoteRequestDTO;
import com.floriano.philosophy_api.dto.QuoteDTO.QuoteResponseDTO;
import com.floriano.philosophy_api.model.Philosopher.Philosopher;
import com.floriano.philosophy_api.model.Quote.Quote;
import com.floriano.philosophy_api.model.Theme.Theme;
import com.floriano.philosophy_api.model.Work.Work;

import java.util.List;
import java.util.stream.Collectors;

public class QuoteMapper {

    public static Quote toEntity(QuoteRequestDTO dto, Philosopher philosopher, Work work, List<Theme> themes) {
        Quote quote = new Quote();

        quote.setContent(dto.getContent());
        quote.setPhilosopher(philosopher);
        quote.setWork(work);

        for (Theme t : themes) {
            quote.addTheme(t);
        }

        return quote;
    }

    public static QuoteResponseDTO toDTO(Quote quote) {
        return new QuoteResponseDTO(
                quote.getId(),
                quote.getContent(),
                quote.getPhilosopher().getName(),
                quote.getWork() != null ? quote.getWork().getTitle() : null
        );
    }
}
