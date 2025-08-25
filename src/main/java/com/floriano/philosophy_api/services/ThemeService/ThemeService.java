package com.floriano.philosophy_api.services.ThemeService;

import com.floriano.philosophy_api.dto.PhilosopherDTO.PhilosopherResponseDTO;
import com.floriano.philosophy_api.dto.QuoteDTO.QuoteResponseDTO;
import com.floriano.philosophy_api.dto.ThemeDTO.ThemeRequestDTO;
import com.floriano.philosophy_api.dto.ThemeDTO.ThemeResponseDTO;
import com.floriano.philosophy_api.dto.WorkDTO.WorkResponseDTO;
import com.floriano.philosophy_api.exceptions.ThemeIdNotFoundException;
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
import com.floriano.philosophy_api.specification.ThemeSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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

    public Page<ThemeResponseDTO> getAllThemes(Pageable pageable) {
        Page<Theme> themes = themeRepository.findAll(pageable);
        return themes.map(ThemeMapper::toDTO);
    }

    public Page<PhilosopherResponseDTO> getPhilosophersByTheme(Long themeId, Pageable pageable) {
        Theme theme = themeRepository.findById(themeId)
                .orElseThrow(() -> new ThemeIdNotFoundException("Theme not found"));
        List<PhilosopherResponseDTO> dtos = theme.getPhilosophers()
                .stream()
                .map(p -> new PhilosopherResponseDTO(p.getId(), p.getName(), p.getBirthYear(), p.getDeathYear(), p.getBio(), p.getCountry().getName()))
                .toList();
        return new PageImpl<>(dtos, pageable, dtos.size());
    }

    public Page<WorkResponseDTO> getWorksByTheme(Long themeId, Pageable pageable) {
        Theme theme = themeRepository.findById(themeId)
                .orElseThrow(() -> new ThemeIdNotFoundException("Theme not found"));
        List<WorkResponseDTO> dtos = theme.getWorks()
                .stream()
                .map(w -> new WorkResponseDTO(w.getId(), w.getTitle(), w.getYear(), w.getSummary(), w.getPhilosopher().getName(), w.getCountry().getName()))
                .toList();
        return new PageImpl<>(dtos, pageable, dtos.size());
    }

    public Page<QuoteResponseDTO> getQuotesByTheme(Long themeId, Pageable pageable) {
        Theme theme = themeRepository.findById(themeId)
                .orElseThrow(() -> new ThemeIdNotFoundException("Theme not found"));

        List<QuoteResponseDTO> dtos = theme.getQuotes()
                .stream()
                .map(q -> new QuoteResponseDTO(q.getId(), q.getContent(), q.getPhilosopher() != null ? q.getPhilosopher().getName() : null,
                        q.getWork() != null ? q.getWork().getTitle() : "No work associated"))
                .toList();

        return new PageImpl<>(dtos, pageable, dtos.size());
    }

    public Page<ThemeResponseDTO> searchThemes(String name, Pageable pageable) {
        Specification<Theme> spec = ThemeSpecification.hasName(name);

        return themeRepository.findAll(spec, pageable).map(ThemeMapper::toDTO);
    }

    public ThemeResponseDTO getThemeById(Long id) {
        Theme theme = themeRepository.findById(id)
                .orElseThrow(() -> new ThemeIdNotFoundException("Theme not found"));

        return ThemeMapper.toDTO(theme);
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

        if (dto.getPhilosophersIds() != null && !dto.getPhilosophersIds().isEmpty()) {
            ThemeUpdateHelper.updatePhilosophers(existing, dto.getPhilosophersIds(), philosopherRepository);
        }

        if (dto.getWorksIds() != null && !dto.getWorksIds().isEmpty()) {
            ThemeUpdateHelper.updateWorks(existing, dto.getWorksIds(), workRepository);
        }

        if (dto.getQuotesIds() != null && !dto.getQuotesIds().isEmpty()) {
            ThemeUpdateHelper.updateQuotes(existing, dto.getQuotesIds(), quoteRepository);
        }

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
