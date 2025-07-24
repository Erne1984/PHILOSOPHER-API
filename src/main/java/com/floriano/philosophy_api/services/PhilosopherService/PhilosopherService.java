package com.floriano.philosophy_api.services.PhilosopherService;

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

    public Philosopher createPhilosopher(Philosopher p) {
        if (p.getCountry() != null) {
            Country country = countryRepository.findById(p.getCountry().getId())
                    .orElseThrow(() -> new IllegalArgumentException("País não encontrado"));
            p.setCountry(country);
        }
        return philosopherRepository.save(p);
    }

    public List<Philosopher> getAllPhilosopher() {
        return philosopherRepository.findAll();
    }
}
