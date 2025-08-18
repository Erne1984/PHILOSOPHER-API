package com.floriano.philosophy_api.services.WorkService;

import com.floriano.philosophy_api.dto.QuoteDTO.QuoteResponseDTO;
import com.floriano.philosophy_api.dto.WorkDTO.WorkRequestDTO;
import com.floriano.philosophy_api.dto.WorkDTO.WorkResponseDTO;
import com.floriano.philosophy_api.exceptions.WorkIdNotFoundException;
import com.floriano.philosophy_api.mapper.PhilosopherMapper;
import com.floriano.philosophy_api.mapper.QuoteMapper;
import com.floriano.philosophy_api.mapper.WorkMapper;
import com.floriano.philosophy_api.model.Country.Country;
import com.floriano.philosophy_api.model.Philosopher.Philosopher;
import com.floriano.philosophy_api.model.Quote.Quote;
import com.floriano.philosophy_api.model.Work.Work;
import com.floriano.philosophy_api.repositories.CountryRepository.CountryRepository;
import com.floriano.philosophy_api.repositories.PhilosopherRepository.PhilosopherRepository;
import com.floriano.philosophy_api.repositories.QuoteRepository.QuoteRepository;
import com.floriano.philosophy_api.repositories.SchoolOfThoughtRepository.SchoolOfThoughtRepository;
import com.floriano.philosophy_api.repositories.ThemeRepository.ThemeRepository;
import com.floriano.philosophy_api.repositories.WorkRepository.WorkRepository;
import com.floriano.philosophy_api.services.WorkService.utils.WorkDeleteHelper;
import com.floriano.philosophy_api.services.WorkService.utils.WorkUpdateHelper;
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

    public List<WorkResponseDTO> getAllWorks() {
        List<Work> works = workRepository.findAll();

        return works.stream()
                .map(WorkMapper::toDTO)
                .toList();
    }

    public WorkResponseDTO getWorkById(Long id) {
        Work work = workRepository.findById(id)
                .orElseThrow(() -> new WorkIdNotFoundException("Work not found"));

        return WorkMapper.toDTO(work);
    }

    public List<QuoteResponseDTO> getQuotesByWork(Long id) {
        Work work = workRepository.findById(id)
                .orElseThrow(() -> new WorkIdNotFoundException("Work not found"));

        List<Quote> quoteList = quoteRepository.findByWorkId(work.getId());

        return quoteList.
                stream()
                .map(QuoteMapper::toDTO)
                .toList();
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
        Work work = workRepository.findById(id)
                .orElseThrow(() -> new WorkIdNotFoundException("Work not found"));

        WorkUpdateHelper.updateBasicFields(work, dto);
        WorkUpdateHelper.updatePhilosopher(work, dto, philosopherRepository);
        WorkUpdateHelper.updateCountry(work, dto, countryRepository);
        WorkUpdateHelper.updateSchoolOfThoughts(work, dto.getSchoolOfThoughtIds(), schoolOfThoughtRepository);
        WorkUpdateHelper.updateThemes(work, dto.getThemeIds(), themeRepository);

        return workRepository.save(work);
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
