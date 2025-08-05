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
import com.floriano.philosophy_api.services.ThemeService.utils.ThemeDeleteHelper;
import com.floriano.philosophy_api.services.ThemeService.utils.ThemeUpdateHelper;
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

        ThemeUpdateHelper.updateBasicFields(existing, dto);
        ThemeUpdateHelper.updatePhilosophers(existing, dto.getPhilosophersIds(), philosopherRepository);
        ThemeUpdateHelper.updateWorks(existing, dto.getWorksIds(), workRepository);
        ThemeUpdateHelper.updateQuotes(existing, dto.getQuotesIds(), quoteRepository);

        return themeRepository.save(existing);
    }

   public Theme deleteThemeById(Long id) {
       Theme theme = themeRepository.findById(id)
               .orElseThrow(() -> new ThemeIdNotFoundException("Theme not found"));

       ThemeDeleteHelper.detachAllRelationships(theme);
       themeRepository.save(theme);
       themeRepository.delete(theme);

       return theme;
    }
}
