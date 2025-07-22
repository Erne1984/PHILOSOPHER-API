package com.floriano.login_system_backend_maven.services.PhilosopherService;

import com.floriano.login_system_backend_maven.model.Country.Country;
import com.floriano.login_system_backend_maven.model.Philosopher.Philosopher;
import com.floriano.login_system_backend_maven.repositories.CountryRepository.CountryRepository;
import com.floriano.login_system_backend_maven.repositories.PhilosopherRepository.PhilosopherRepository;
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
