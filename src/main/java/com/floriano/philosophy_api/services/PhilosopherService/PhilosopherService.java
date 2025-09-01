package com.floriano.philosophy_api.services.PhilosopherService;

import com.floriano.philosophy_api.dto.InfluenceDTO.InfluenceResponseDTO;
import com.floriano.philosophy_api.dto.PhilosopherDTO.PhilosopherRequestDTO;
import com.floriano.philosophy_api.dto.PhilosopherDTO.PhilosopherResponseDTO;
import com.floriano.philosophy_api.dto.QuoteDTO.QuoteResponseDTO;
import com.floriano.philosophy_api.dto.WorkDTO.WorkResponseDTO;
import com.floriano.philosophy_api.exceptions.PhilosopherAlreadyExistsException;
import com.floriano.philosophy_api.exceptions.PhilosopherIdNotFoundException;
import com.floriano.philosophy_api.exceptions.ThemeIdNotFoundException;
import com.floriano.philosophy_api.mapper.PhilosopherMapper;
import com.floriano.philosophy_api.mapper.QuoteMapper;
import com.floriano.philosophy_api.mapper.WorkMapper;
import com.floriano.philosophy_api.model.Country.Country;
import com.floriano.philosophy_api.model.Influence.Influence;
import com.floriano.philosophy_api.model.Influence.InfluenceStrength;
import com.floriano.philosophy_api.model.Philosopher.Philosopher;
import com.floriano.philosophy_api.model.Quote.Quote;
import com.floriano.philosophy_api.model.Theme.Theme;
import com.floriano.philosophy_api.model.Work.Work;
import com.floriano.philosophy_api.repositories.CountryRepository.CountryRepository;
import com.floriano.philosophy_api.repositories.InfluenceRepository.InfluenceRepository;
import com.floriano.philosophy_api.repositories.PhilosopherRepository.PhilosopherRepository;
import com.floriano.philosophy_api.repositories.QuoteRepository.QuoteRepository;
import com.floriano.philosophy_api.repositories.SchoolOfThoughtRepository.SchoolOfThoughtRepository;
import com.floriano.philosophy_api.repositories.ThemeRepository.ThemeRepository;
import com.floriano.philosophy_api.repositories.WorkRepository.WorkRepository;
import com.floriano.philosophy_api.services.PhilosopherService.utils.PhilosopherDeleteHelper;
import com.floriano.philosophy_api.services.PhilosopherService.utils.PhilosopherUpdateHelper;
import com.floriano.philosophy_api.specification.PhilosopherSpecification;
import com.floriano.philosophy_api.specification.QuoteSpecification;
import com.floriano.philosophy_api.specification.WorkSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhilosopherService {

    private final PhilosopherRepository philosopherRepository;
    private final CountryRepository countryRepository;
    private final QuoteRepository quoteRepository;
    private final SchoolOfThoughtRepository schoolOfThoughtRepository;
    private final ThemeRepository themeRepository;
    private final WorkRepository workRepository;
    private final InfluenceRepository influenceRepository;

    public PhilosopherService(PhilosopherRepository philosopherRepository,
                              CountryRepository countryRepository,
                              QuoteRepository quoteRepository,
                              SchoolOfThoughtRepository schoolOfThoughtRepository,
                              ThemeRepository themeRepository,
                              WorkRepository workRepository,
                              InfluenceRepository influenceRepository) {
        this.philosopherRepository = philosopherRepository;
        this.countryRepository = countryRepository;
        this.quoteRepository = quoteRepository;
        this.schoolOfThoughtRepository = schoolOfThoughtRepository;
        this.themeRepository = themeRepository;
        this.workRepository = workRepository;
        this.influenceRepository = influenceRepository;
    }

    public List<Philosopher> getAllPhilosophers() {
        return philosopherRepository.findAll();
    }

    public Philosopher getPhilosopherById(Long id) {
        return philosopherRepository.findById(id)
                .orElseThrow(() -> new PhilosopherIdNotFoundException("Filósofo com ID " + id + " não encontrado"));
    }

    public List<QuoteResponseDTO> getQuotesByPhilosopher(Long id) {

        Philosopher philosopher = philosopherRepository.findById(id)
                .orElseThrow(() -> new PhilosopherIdNotFoundException("Philosopher with ID " + id + " not found"));

        List<Quote> quotes = quoteRepository.findByPhilosopherId(id);

        List<QuoteResponseDTO> quotesResponse = quotes.stream()
                .map(QuoteMapper::toDTO)
                .toList();

        return quotesResponse;
    }

    public List<WorkResponseDTO> getWorksByPhilosopher(Long id) {

        Philosopher philosopher = philosopherRepository.findById(id)
                .orElseThrow(() -> new PhilosopherIdNotFoundException("Philosopher with ID " + id + " not found"));

        List<Work> workList = workRepository.findByPhilosopherId(id);

        List<WorkResponseDTO> workResponseDTOS = workList.stream()
                .map(WorkMapper::toDTO)
                .toList();

        return workResponseDTOS;
    }

    public List<InfluenceResponseDTO> getInfluencedByPhilosopher(Long id) {
        Philosopher philosopher = philosopherRepository.findById(id)
                .orElseThrow(() -> new PhilosopherIdNotFoundException("Philosopher with ID " + id + " not found"));

        return philosopher.getInfluenced().stream()
                .map(influence -> new InfluenceResponseDTO(
                        influence.getId(),
                        philosopher.getName(), // influencer
                        influence.getInfluenced().getName(), // influenced
                        influence.getStrength().name()
                ))
                .toList();
    }

    public List<InfluenceResponseDTO> getInfluencersOfPhilosopher(Long id) {
        Philosopher philosopher = philosopherRepository.findById(id)
                .orElseThrow(() -> new PhilosopherIdNotFoundException("Philosopher with ID " + id + " not found"));

        return philosopher.getInfluencedBy().stream()
                .map(influence -> new InfluenceResponseDTO(
                        influence.getId(),
                        influence.getInfluencer().getName(), // influencer
                        philosopher.getName(), // influenced
                        influence.getStrength().name()
                ))
                .toList();
    }

    public PhilosopherResponseDTO getRandomPhilosopher() {
        Philosopher philosopher = philosopherRepository.findRandomPhilosopher();
        return PhilosopherMapper.toDTO(philosopher);
    }

    public Page<PhilosopherResponseDTO> searchPhilosophers(String name, String countryName, String schoolName, Integer startYear, Integer endYear, Pageable pageable) {
        Specification<Philosopher> spec = (root, query, cb) -> cb.conjunction();

        spec = spec.and(PhilosopherSpecification.hasName(name))
                .and(PhilosopherSpecification.hasCountryName(countryName))
                .and(PhilosopherSpecification.hasSchoolName(schoolName))
                .and(PhilosopherSpecification.bornGreaterThanOrEqualTo(startYear))
                .and(PhilosopherSpecification.bornBeforeThanOrEqualTo(endYear));

        return philosopherRepository.findAll(spec, pageable).map(PhilosopherMapper::toDTO);
    }

    public void addThemeToPhilosopher(Long philosopherId, Long themeId) {
        Philosopher philosopher = philosopherRepository.findById(philosopherId)
                .orElseThrow(() -> new PhilosopherIdNotFoundException("Philosopher not found!"));
        Theme theme = themeRepository.findById(themeId)
                .orElseThrow(() -> new ThemeIdNotFoundException("Theme not found!"));

        philosopher.addTheme(theme);
        philosopherRepository.save(philosopher);
    }

    public void removeThemeFromPhilosopher(Long philosopherId, Long themeId) {
        Philosopher philosopher = philosopherRepository.findById(philosopherId)
                .orElseThrow(() -> new PhilosopherIdNotFoundException("Philosopher not found!"));
        Theme theme = themeRepository.findById(themeId)
                .orElseThrow(() -> new ThemeIdNotFoundException("Theme not found!"));

        philosopher.removeTheme(theme);
        philosopherRepository.save(philosopher);
    }

    public Influence addInfluence(Long influencerId, Long influencedId, InfluenceStrength strength) {
        Philosopher influencer = philosopherRepository.findById(influencerId)
                .orElseThrow(() -> new PhilosopherIdNotFoundException("Influencer not found!"));
        Philosopher influenced = philosopherRepository.findById(influencedId)
                .orElseThrow(() -> new PhilosopherIdNotFoundException("Influenced philosopher not found!"));

        Influence influence = new Influence();
        influence.setId(null);
        influence.setInfluencer(influencer);
        influence.setInfluenced(influenced);
        influence.setStrength(strength);

        return influenceRepository.save(influence);
    }

    public void removeInfluence(Long influenceId) {
        if (!influenceRepository.existsById(influenceId)) {
            throw new IllegalArgumentException("Influence with ID " + influenceId + " not found!");
        }
        influenceRepository.deleteById(influenceId);
    }

    public Philosopher createPhilosopher(PhilosopherRequestDTO dto) {

        if (philosopherRepository.existsByNameIgnoreCase(dto.getName())) {
            throw new PhilosopherAlreadyExistsException("Filósofo com esse nome já existe");
        }

        Country country = countryRepository.findById(dto.getCountryId())
                .orElseThrow(() -> new IllegalArgumentException("País não encontrado"));

        Philosopher philosopher = PhilosopherMapper.toEntity(dto, country);

        if (dto.getQuotesId() != null && !dto.getQuotesId().isEmpty()) {
            var quotes = quoteRepository.findAllById(dto.getQuotesId());
            if (quotes.size() != dto.getQuotesId().size()) {
                throw new IllegalArgumentException("Uma ou mais citações não foram encontradas.");
            }
            philosopher.setQuotes(quotes);
        }

        if (dto.getSchoolOfThoughtsIds() != null && !dto.getSchoolOfThoughtsIds().isEmpty()) {
            var schools = schoolOfThoughtRepository.findAllById(dto.getSchoolOfThoughtsIds());
            if (schools.size() != dto.getSchoolOfThoughtsIds().size()) {
                throw new IllegalArgumentException("Uma ou mais escolas de pensamento não foram encontradas.");
            }
            for (var s : schools) {
                philosopher.addSchoolOfThought(s);
            }
        }

        if (dto.getThemesIds() != null && !dto.getThemesIds().isEmpty()) {
            var themes = themeRepository.findAllById(dto.getThemesIds());
            if (themes.size() != dto.getThemesIds().size()) {
                throw new IllegalArgumentException("Um ou mais temas não foram encontrados.");
            }
            for (var t : themes) {
                philosopher.addTheme(t);
            }
        }

        return philosopherRepository.save(philosopher);
    }

    public Philosopher updatePhilosopher(Long id, PhilosopherRequestDTO dto) {
        Philosopher philosopher = philosopherRepository.findById(id)
                .orElseThrow(() -> new PhilosopherIdNotFoundException("Filósofo com ID " + id + " não encontrado"));

        PhilosopherUpdateHelper.updateBasicFields(philosopher, dto);
        PhilosopherUpdateHelper.updateCountry(philosopher, dto, countryRepository);
        PhilosopherUpdateHelper.updateQuotes(philosopher, dto.getQuotesId(), quoteRepository);
        PhilosopherUpdateHelper.updateSchoolOfThoughts(philosopher, dto.getSchoolOfThoughtsIds(), schoolOfThoughtRepository);
        PhilosopherUpdateHelper.updateThemes(philosopher, dto.getThemesIds(), themeRepository);

        return philosopherRepository.save(philosopher);
    }

    public Philosopher deletePhilosopher(Long id) {
        Philosopher philosopher = philosopherRepository.findById(id)
                .orElseThrow(() -> new PhilosopherIdNotFoundException("Philosopher with ID " + id + " not found"));

        PhilosopherDeleteHelper.detachAllRelationships(philosopher);

        philosopherRepository.delete(philosopher);

        return philosopher;
    }
}