package com.floriano.philosophy_api.services.PhilosopherService;

import com.floriano.philosophy_api.dto.PhilosopherDTO.PhilosopherRequestDTO;
import com.floriano.philosophy_api.dto.QuoteDTO.QuoteResponseDTO;
import com.floriano.philosophy_api.exceptions.PhilosopherAlreadyExistsException;
import com.floriano.philosophy_api.exceptions.PhilosopherIdNotFoundException;
import com.floriano.philosophy_api.mapper.PhilosopherMapper;
import com.floriano.philosophy_api.model.Country.Country;
import com.floriano.philosophy_api.model.Philosopher.Philosopher;
import com.floriano.philosophy_api.model.Quote.Quote;
import com.floriano.philosophy_api.repositories.CountryRepository.CountryRepository;
import com.floriano.philosophy_api.repositories.PhilosopherRepository.PhilosopherRepository;
import com.floriano.philosophy_api.repositories.QuoteRepository.QuoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhilosopherService {

    private final PhilosopherRepository philosopherRepository;
    private final CountryRepository countryRepository;
    private final QuoteRepository quoteRepository;

    public PhilosopherService(PhilosopherRepository philosopherRepository, CountryRepository countryRepository, QuoteRepository quoteRepository) {
        this.philosopherRepository = philosopherRepository;
        this.countryRepository = countryRepository;
        this.quoteRepository = quoteRepository;
    }

    public Philosopher createPhilosopher(PhilosopherRequestDTO dto) {

        if (philosopherRepository.existsByNameIgnoreCase(dto.getName())) {
            throw new PhilosopherAlreadyExistsException("Filósofo com esse nome já existe");
        }

        Country country = countryRepository.findById(dto.getCountryId())
                .orElseThrow(() -> new IllegalArgumentException("País não encontrado"));

        Philosopher philosopher = PhilosopherMapper.toEntity(dto, country);
        return philosopherRepository.save(philosopher);
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
                .map(quote -> {
                    return new QuoteResponseDTO(
                            quote.getId(),
                            quote.getContent(),
                            philosopher.getName(),
                            quote.getWork() != null ? quote.getWork().getTitle() : null,
                            quote.getThemes() != null
                                    ? quote.getThemes().stream().map(theme -> theme.getName()).toList()
                                    : List.of()
                    );
                }).toList();

        return quotesResponse;
    }
}