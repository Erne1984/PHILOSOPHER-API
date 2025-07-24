package com.floriano.philosophy_api.services.PhilosopherService;

import com.floriano.philosophy_api.dto.PhilosopherDto.PhilosopherRequestDTO;
import com.floriano.philosophy_api.exceptions.PhilosopherAlreadyExistsException;
import com.floriano.philosophy_api.exceptions.PhilosopherIdNotFoundException;
import com.floriano.philosophy_api.mapper.PhilosopherMapper;
import com.floriano.philosophy_api.model.Country.Country;
import com.floriano.philosophy_api.model.Philosopher.Philosopher;
import com.floriano.philosophy_api.repositories.CountryRepository.CountryRepository;
import com.floriano.philosophy_api.repositories.PhilosopherRepository.PhilosopherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhilosopherService {

    private final PhilosopherRepository philosopherRepository;
    private final CountryRepository countryRepository;

    public PhilosopherService(PhilosopherRepository philosopherRepository,CountryRepository countryRepository) {
        this.philosopherRepository = philosopherRepository;
        this.countryRepository = countryRepository;
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
}