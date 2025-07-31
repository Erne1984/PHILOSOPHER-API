package com.floriano.philosophy_api.services.QuoteService;

import com.floriano.philosophy_api.dto.QuoteDTO.QuoteRequestDTO;
import com.floriano.philosophy_api.dto.QuoteDTO.QuoteResponseDTO;
import com.floriano.philosophy_api.mapper.QuoteMapper;
import com.floriano.philosophy_api.model.Philosopher.Philosopher;
import com.floriano.philosophy_api.model.Quote.Quote;
import com.floriano.philosophy_api.model.Work.Work;
import com.floriano.philosophy_api.repositories.PhilosopherRepository.PhilosopherRepository;
import com.floriano.philosophy_api.repositories.QuoteRepository.QuoteRepository;
import com.floriano.philosophy_api.repositories.WorkRepository.WorkRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuoteService {

    private final QuoteRepository quoteRepository;
    private final PhilosopherRepository philosopherRepository;
    private final WorkRepository workRepository;

    public QuoteService(QuoteRepository quoteRepository, PhilosopherRepository philosopherRepository, WorkRepository workRepository) {
        this.quoteRepository = quoteRepository;
        this.philosopherRepository = philosopherRepository;
        this.workRepository = workRepository;
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

        Quote quote = QuoteMapper.toEntity(dto, philosopher, work);

        return quoteRepository.save(quote);
    }
}
