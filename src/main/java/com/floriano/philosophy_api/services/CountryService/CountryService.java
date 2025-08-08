package com.floriano.philosophy_api.services.CountryService;

import com.floriano.philosophy_api.dto.CountryDTO.CountryRequestDTO;
import com.floriano.philosophy_api.exceptions.CountryNotFoundException;
import com.floriano.philosophy_api.exceptions.WorkIdNotFoundException;
import com.floriano.philosophy_api.mapper.CountryMapper;
import com.floriano.philosophy_api.model.Country.Country;
import com.floriano.philosophy_api.model.Philosopher.Philosopher;
import com.floriano.philosophy_api.model.Work.Work;
import com.floriano.philosophy_api.repositories.CountryRepository.CountryRepository;
import com.floriano.philosophy_api.repositories.PhilosopherRepository.PhilosopherRepository;
import com.floriano.philosophy_api.repositories.WorkRepository.WorkRepository;
import com.floriano.philosophy_api.services.CountryService.utils.CountryDeleteHelper;
import com.floriano.philosophy_api.services.CountryService.utils.CountryUpdateHelper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryService {

    private final CountryRepository countryRepository;
    private final PhilosopherRepository philosopherRepository;
    private final WorkRepository workRepository;

    public CountryService(CountryRepository countryRepository, PhilosopherRepository philosopherRepository, WorkRepository workRepository) {
        this.countryRepository = countryRepository;
        this.philosopherRepository = philosopherRepository;
        this.workRepository = workRepository;
    }

    public List<Country> getCountry() {
        return countryRepository.findAll();
    }

    public Country createCountry(CountryRequestDTO dto) {
        Country country = CountryMapper.toEntity(dto);

        return countryRepository.save(country);
    }

    public Country updateCountry(Long id, CountryRequestDTO dto) {
        Country country = countryRepository.findById(id)
                .orElseThrow(() -> new CountryNotFoundException("Country not found"));

        CountryUpdateHelper.updateBasicFields(country, dto);

        return countryRepository.save(country);
    }

    public Country deleteCountryById(Long id) {
        Country country = countryRepository.findById(id)
                .orElseThrow(() -> new CountryNotFoundException("Country not found"));

        CountryDeleteHelper.detachAllRelationShips(country, philosopherRepository, workRepository);

        countryRepository.delete(country);

        return country;
    }

}
