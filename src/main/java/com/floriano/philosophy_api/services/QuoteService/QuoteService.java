package com.floriano.philosophy_api.services.QuoteService;

import com.floriano.philosophy_api.dto.QuoteDTO.QuoteRequestDTO;
import com.floriano.philosophy_api.dto.QuoteDTO.QuoteResponseDTO;
import com.floriano.philosophy_api.mapper.QuoteMapper;
import com.floriano.philosophy_api.model.Philosopher.Philosopher;
import com.floriano.philosophy_api.model.Quote.Quote;
import com.floriano.philosophy_api.model.Theme.Theme;
import com.floriano.philosophy_api.model.Work.Work;
import com.floriano.philosophy_api.repositories.PhilosopherRepository.PhilosopherRepository;
import com.floriano.philosophy_api.repositories.QuoteRepository.QuoteRepository;
import com.floriano.philosophy_api.repositories.ThemeRepository.ThemeRepository;
import com.floriano.philosophy_api.repositories.WorkRepository.WorkRepository;
import com.floriano.philosophy_api.services.QuoteService.utils.QuoteUpdateHelper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuoteService {

    private final QuoteRepository quoteRepository;
    private final PhilosopherRepository philosopherRepository;
    private final WorkRepository workRepository;
    private final ThemeRepository themeRepository;

    public QuoteService(QuoteRepository quoteRepository, PhilosopherRepository philosopherRepository, WorkRepository workRepository, ThemeRepository themeRepository) {
        this.quoteRepository = quoteRepository;
        this.philosopherRepository = philosopherRepository;
        this.workRepository = workRepository;
        this.themeRepository = themeRepository;
    }

    public List<QuoteResponseDTO> getAllQuotes() {
        List<Quote> quotes = quoteRepository.findAll();

        List<QuoteResponseDTO> quoteResponseDTOList = quotes.stream()
                .map(QuoteMapper::toDTO)
                .toList();

        return quoteResponseDTOList;
    }


    public Quote createQuote(QuoteRequestDTO dto) {

        Philosopher philosopher = philosopherRepository.findById(dto.getPhilosopherId())
                .orElseThrow(() -> new IllegalArgumentException("Filósofo não encontrado"));

        Work work = workRepository.findById(dto.getWorkId())
                .orElseThrow(() -> new IllegalArgumentException("Obra não encontrada"));

        List<Theme> themes = themeRepository.findAllById(dto.getThemesId());

        if (themes.size() != dto.getThemesId().size()) {
            throw new IllegalArgumentException("Um ou mais temas não foram encontrados.");
        }

        Quote quote = QuoteMapper.toEntity(dto, philosopher, work, themes);

        return quoteRepository.save(quote);
    }

    public Quote updateQuote(Long id, QuoteRequestDTO dto) {
        Quote existing = quoteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quote not found"));

        QuoteUpdateHelper.updateBasicFields(existing, dto);
        QuoteUpdateHelper.updatePhilosopher(existing, dto, philosopherRepository);
        QuoteUpdateHelper.updateWork(existing, dto, workRepository);
        QuoteUpdateHelper.updateThemes(existing, dto.getThemesId(), themeRepository);

        return quoteRepository.save(existing);
    }
}
