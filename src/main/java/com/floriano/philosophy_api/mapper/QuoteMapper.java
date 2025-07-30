package com.floriano.philosophy_api.mapper;

import com.floriano.philosophy_api.dto.QuoteDTO.QuoteRequestDTO;
import com.floriano.philosophy_api.dto.QuoteDTO.QuoteResponseDTO;
import com.floriano.philosophy_api.model.Philosopher.Philosopher;
import com.floriano.philosophy_api.model.Quote.Quote;
import com.floriano.philosophy_api.model.Work.Work;

import java.util.List;
import java.util.stream.Collectors;

public class QuoteMapper {

    public static Quote toEntity(QuoteRequestDTO dto, Philosopher philosopher, Work work) {
        Quote quote = new Quote();

        quote.setContent(dto.getContent());
        quote.setWork(work);
        quote.setPhilosopher(philosopher);

        // TO IMPLEMENTE YET
        quote.setThemes(null);

        return quote;
    }

    public static QuoteResponseDTO toDTO(Quote quote) {
        return new QuoteResponseDTO(
                quote.getId(),
                quote.getContent(),
                quote.getPhilosopher().getName(),
                quote.getWork().getTitle(),
                quote.getThemes() != null
                        ? quote.getThemes().stream().map(theme -> theme.getName()).collect(Collectors.toList())
                        : List.of()
        );
    }
}
