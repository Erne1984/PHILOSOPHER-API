package com.floriano.philosophy_api.services.WorkService;

import com.floriano.philosophy_api.dto.QuoteDTO.QuoteResponseDTO;
import com.floriano.philosophy_api.dto.ThemeDTO.ThemeResponseDTO;
import com.floriano.philosophy_api.dto.WorkDTO.WorkRequestDTO;
import com.floriano.philosophy_api.dto.WorkDTO.WorkResponseDTO;
import com.floriano.philosophy_api.exceptions.SchoolOfThoghtNotFoundException;
import com.floriano.philosophy_api.exceptions.ThemeIdNotFoundException;
import com.floriano.philosophy_api.exceptions.WorkIdNotFoundException;
import com.floriano.philosophy_api.mapper.QuoteMapper;
import com.floriano.philosophy_api.mapper.ThemeMapper;
import com.floriano.philosophy_api.mapper.WorkMapper;
import com.floriano.philosophy_api.model.Country.Country;
import com.floriano.philosophy_api.model.Philosopher.Philosopher;
import com.floriano.philosophy_api.model.Quote.Quote;
import com.floriano.philosophy_api.model.SchoolOfThought.SchoolOfThought;
import com.floriano.philosophy_api.model.Theme.Theme;
import com.floriano.philosophy_api.model.Work.Work;
import com.floriano.philosophy_api.repositories.CountryRepository.CountryRepository;
import com.floriano.philosophy_api.repositories.PhilosopherRepository.PhilosopherRepository;
import com.floriano.philosophy_api.repositories.QuoteRepository.QuoteRepository;
import com.floriano.philosophy_api.repositories.SchoolOfThoughtRepository.SchoolOfThoughtRepository;
import com.floriano.philosophy_api.repositories.ThemeRepository.ThemeRepository;
import com.floriano.philosophy_api.repositories.WorkRepository.WorkRepository;
import com.floriano.philosophy_api.services.WorkService.utils.WorkDeleteHelper;
import com.floriano.philosophy_api.services.WorkService.utils.WorkUpdateHelper;
import com.floriano.philosophy_api.specification.WorkSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkService {

    private final WorkRepository workRepository;
    private final PhilosopherRepository philosopherRepository;
    private final CountryRepository countryRepository;
    private final SchoolOfThoughtRepository schoolOfThoughtRepository;
    private final ThemeRepository themeRepository;
    private final QuoteRepository quoteRepository;

    public WorkService(WorkRepository workRepository,
                       PhilosopherRepository philosopherRepository,
                       CountryRepository countryRepository,
                       SchoolOfThoughtRepository schoolOfThoughtRepository,
                       ThemeRepository themeRepository,
                       QuoteRepository quoteRepository) {
        this.workRepository = workRepository;
        this.philosopherRepository = philosopherRepository;
        this.countryRepository = countryRepository;
        this.schoolOfThoughtRepository = schoolOfThoughtRepository;
        this.themeRepository = themeRepository;
        this.quoteRepository = quoteRepository;
    }

    public Page<WorkResponseDTO> getAllWorks(Pageable pageable) {
        return workRepository.findAll(pageable).map(WorkMapper::toDTO);
    }

    public WorkResponseDTO getWorkById(Long id) {
        Work work = workRepository.findById(id)
                .orElseThrow(() -> new WorkIdNotFoundException("Work not found"));
        return WorkMapper.toDTO(work);
    }

    public Page<QuoteResponseDTO> getQuotesByWork(Long id, Pageable pageable) {
        Work work = workRepository.findById(id)
                .orElseThrow(() -> new WorkIdNotFoundException("Work not found"));

        List<QuoteResponseDTO> dtos = quoteRepository.findByWorkId(work.getId())
                .stream()
                .map(QuoteMapper::toDTO)
                .toList();

        return new PageImpl<>(dtos, pageable, dtos.size());
    }

    public Page<ThemeResponseDTO> getThemesByWork(Long id, Pageable pageable) {
        Work work = workRepository.findById(id)
                .orElseThrow(() -> new WorkIdNotFoundException("Work not found"));

        List<ThemeResponseDTO> dtos = work.getThemes()
                .stream()
                .map(ThemeMapper::toDTO)
                .toList();

        return new PageImpl<>(dtos, pageable, dtos.size());
    }

    public Page<WorkResponseDTO> searchWorks(
            String title,
            String philosopherName,
            Integer startYear,
            Integer endYear,
            Pageable pageable
    ) {
        Specification<Work> spec = (root, query, cb) -> cb.conjunction();

        spec = spec.and(WorkSpecification.hasTitle(title))
                .and(WorkSpecification.hasPhilosopherName(philosopherName))
                .and(WorkSpecification.yearGreaterThanOrEqualTo(startYear))
                .and(WorkSpecification.yearLessThanOrEqualTo(endYear));

        return workRepository.findAll(spec, pageable).map(WorkMapper::toDTO);
    }

    public void addThemeToWork(Long workId, Long themeId) {
        Work work = workRepository.findById(workId)
                .orElseThrow(() -> new WorkIdNotFoundException("Work not found"));
        Theme theme = themeRepository.findById(themeId)
                .orElseThrow(() -> new ThemeIdNotFoundException("Theme not found"));

        work.addTheme(theme);
        workRepository.save(work);
    }

    public void removeThemeFromWork(Long workId, Long themeId) {
        Work work = workRepository.findById(workId)
                .orElseThrow(() -> new WorkIdNotFoundException("Work not found"));
        Theme theme = themeRepository.findById(themeId)
                .orElseThrow(() -> new ThemeIdNotFoundException("Theme not found"));

        work.removeTheme(theme);
        workRepository.save(work);
    }

    public void addSchoolToWork(Long workId, Long schoolId) {
        Work work = workRepository.findById(workId)
                .orElseThrow(() -> new WorkIdNotFoundException("Work not found"));
        SchoolOfThought school = schoolOfThoughtRepository.findById(schoolId)
                .orElseThrow(() -> new SchoolOfThoghtNotFoundException("School not found"));

        work.addSchoolOfThought(school);
        workRepository.save(work);
    }

    public void removeSchoolFromWork(Long workId, Long schoolId) {
        Work work = workRepository.findById(workId)
                .orElseThrow(() -> new WorkIdNotFoundException("Work not found"));
        SchoolOfThought school = schoolOfThoughtRepository.findById(schoolId)
                .orElseThrow(() -> new SchoolOfThoghtNotFoundException("School not found"));

        work.removeSchoolOfThought(school);
        workRepository.save(work);
    }

    public Work createWork(WorkRequestDTO dto) {
        Philosopher philosopher = philosopherRepository.findById(dto.getPhilosopherId())
                .orElseThrow(() -> new RuntimeException("Philosopher not found"));

        Country country = countryRepository.findById(dto.getCountryId())
                .orElseThrow(() -> new RuntimeException("Country not found"));

        Work work = WorkMapper.toEntity(dto, philosopher, country);
        return workRepository.save(work);
    }

    public Work updateWork(Long id, WorkRequestDTO dto) {
        Work existing = workRepository.findById(id)
                .orElseThrow(() -> new WorkIdNotFoundException("Work not found"));

        WorkUpdateHelper.updateBasicFields(existing, dto);
        WorkUpdateHelper.updatePhilosopher(existing, dto, philosopherRepository);
        WorkUpdateHelper.updateCountry(existing, dto, countryRepository);
        WorkUpdateHelper.updateSchoolOfThoughts(existing, dto.getSchoolOfThoughtIds(), schoolOfThoughtRepository);
        WorkUpdateHelper.updateThemes(existing, dto.getThemeIds(), themeRepository);

        return workRepository.save(existing);
    }

    public Work deleteWork(Long id) {
        Work work = workRepository.findById(id)
                .orElseThrow(() -> new WorkIdNotFoundException("Work not found"));

        WorkDeleteHelper.detachAllRelationShips(work);
        workRepository.save(work);
        workRepository.delete(work);

        return work;
    }
}
