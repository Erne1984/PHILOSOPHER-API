package com.floriano.philosophy_api.services.ThemeService;

import com.floriano.philosophy_api.dto.ThemeDTO.ThemeRequestDTO;
import com.floriano.philosophy_api.mapper.ThemeMapper;
import com.floriano.philosophy_api.model.Philosopher.Philosopher;
import com.floriano.philosophy_api.model.Quote.Quote;
import com.floriano.philosophy_api.model.Theme.Theme;
import com.floriano.philosophy_api.model.Work.Work;
import com.floriano.philosophy_api.repositories.PhilosopherRepository.PhilosopherRepository;
import com.floriano.philosophy_api.repositories.QuoteRepository.QuoteRepository;
import com.floriano.philosophy_api.repositories.ThemeRepository.ThemeRepository;
import com.floriano.philosophy_api.repositories.WorkRepository.WorkRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThemeService {

    private final ThemeRepository themeRepository;
    private final PhilosopherRepository philosopherRepository;
    private final WorkRepository workRepository;
    private final QuoteRepository quoteRepository;


    public ThemeService(ThemeRepository themeRepository, PhilosopherRepository philosopherRepository, WorkRepository workRepository, QuoteRepository quoteRepository) {
        this.themeRepository = themeRepository;
        this.philosopherRepository = philosopherRepository;
        this.workRepository = workRepository;
        this.quoteRepository = quoteRepository;
    }

    public Theme createTheme(ThemeRequestDTO dto) {

        List<Philosopher> philosophers = philosopherRepository.findAllById(dto.getPhilosophersIds());
        List<Work> works = workRepository.findAllById(dto.getWorksIds());
        List<Quote> quotes = quoteRepository.findAllById(dto.getQuotesIds());

        Theme themeCreated = ThemeMapper.toEntity(dto, philosophers, works, quotes);

        return themeRepository.save(themeCreated);
    }
}
