package com.floriano.philosophy_api.services.ThemeService;

import com.floriano.philosophy_api.dto.ThemeDTO.ThemeRequestDTO;
import com.floriano.philosophy_api.exceptions.PhilosopherIdNotFoundException;
import com.floriano.philosophy_api.exceptions.QuoteIdNotFoundException;
import com.floriano.philosophy_api.exceptions.ThemeIdNotFoundException;
import com.floriano.philosophy_api.exceptions.WorkIdNotFoundException;
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

    public List<Theme> getAllThemes() {
        return themeRepository.findAll();
    }

    public Theme createTheme(ThemeRequestDTO dto) {

        List<Philosopher> philosophers = philosopherRepository.findAllById(dto.getPhilosophersIds());
        List<Work> works = workRepository.findAllById(dto.getWorksIds());
        List<Quote> quotes = quoteRepository.findAllById(dto.getQuotesIds());

        Theme themeCreated = ThemeMapper.toEntity(dto, philosophers, works, quotes);

        return themeRepository.save(themeCreated);
    }

    public Theme updateTheme(Long id, ThemeRequestDTO dto) {

        Theme existing = themeRepository.findById(id)
                .orElseThrow(() -> new ThemeIdNotFoundException("Theme not found"));

        if (dto.getName() != null && !dto.getName().trim().isEmpty()) {
            existing.setName(dto.getName().trim());
        }

        if (dto.getDescription() != null && !dto.getDescription().trim().isEmpty()) {
            existing.setDescription(dto.getDescription().trim());
        }

        if (dto.getPhilosophersIds() != null) {
            List<Long> requestedIds = dto.getPhilosophersIds();
            List<Philosopher> philosophers = philosopherRepository.findAllById(requestedIds);
            if (philosophers.size() != requestedIds.size()) {
                throw new PhilosopherIdNotFoundException("One or more philosophers not found");
            }
            existing.setPhilosophers(philosophers);
        }

        if (dto.getWorksIds() != null) {
            List<Long> requestedIds = dto.getWorksIds();
            List<Work> works = workRepository.findAllById(requestedIds);
            if (works.size() != requestedIds.size()) {
                throw new WorkIdNotFoundException("One or more works not found");
            }
            existing.setWorks(works);
        }

        if (dto.getQuotesIds() != null) {
            List<Long> requestedIds = dto.getQuotesIds();
            List<Quote> quotes = quoteRepository.findAllById(requestedIds);
            if (quotes.size() != requestedIds.size()) {
                throw new QuoteIdNotFoundException("One or more works not found");
            }
            existing.setQuotes(quotes);
        }

        return themeRepository.save(existing);
    }
}
