package com.floriano.login_system_backend_maven.services.CountryService;

import com.floriano.login_system_backend_maven.model.Country.Country;
import com.floriano.login_system_backend_maven.repositories.CountryRepository.CountryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryService {

    private final CountryRepository countryRepository;

    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public Country createCountry(Country c) {
        return countryRepository.save(c);
    }

    public boolean deleteCountryById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }

        if (!countryRepository.existsById(id)) {
            throw new RuntimeException("Entidade não encontrada com o ID: " + id);
        }

        countryRepository.deleteById(id);

        return true;
    }

}
