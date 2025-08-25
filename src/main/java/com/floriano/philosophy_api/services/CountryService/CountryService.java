package com.floriano.philosophy_api.services.CountryService;

import com.floriano.philosophy_api.dto.CountryDTO.CountryRequestDTO;
import com.floriano.philosophy_api.dto.CountryDTO.CountryResponseDTO;
import com.floriano.philosophy_api.dto.PhilosopherDTO.PhilosopherResponseDTO;
import com.floriano.philosophy_api.dto.WorkDTO.WorkResponseDTO;
import com.floriano.philosophy_api.exceptions.CountryNotFoundException;
import com.floriano.philosophy_api.exceptions.WorkIdNotFoundException;
import com.floriano.philosophy_api.mapper.CountryMapper;
import com.floriano.philosophy_api.mapper.PhilosopherMapper;
import com.floriano.philosophy_api.mapper.WorkMapper;
import com.floriano.philosophy_api.model.Country.Country;
import com.floriano.philosophy_api.model.Philosopher.Philosopher;
import com.floriano.philosophy_api.model.Work.Work;
import com.floriano.philosophy_api.repositories.CountryRepository.CountryRepository;
import com.floriano.philosophy_api.repositories.PhilosopherRepository.PhilosopherRepository;
import com.floriano.philosophy_api.repositories.WorkRepository.WorkRepository;
import com.floriano.philosophy_api.services.CountryService.utils.CountryDeleteHelper;
import com.floriano.philosophy_api.services.CountryService.utils.CountryUpdateHelper;
import com.floriano.philosophy_api.specification.CountrySpecification;
import com.floriano.philosophy_api.specification.PhilosopherSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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

    public List<CountryResponseDTO> getCountries() {

        List<Country> countryList = countryRepository.findAll();

        return countryList.stream()
                .map(CountryMapper::toDTO)
                .toList();
    }

    public CountryResponseDTO getCountryById(Long id) {
        Country country = countryRepository.findById(id)
                .orElseThrow(() -> new CountryNotFoundException("Country not Found"));

        return CountryMapper.toDTO(country);
    }

    public List<PhilosopherResponseDTO> getPhilosophersByCountry(Long id) {
        Country country = countryRepository.findById(id)
                .orElseThrow(() -> new CountryNotFoundException("Country not Found"));

        List<Philosopher> philosopherList = philosopherRepository.findByCountryId(country.getId());

        return philosopherList.stream()
                .map(PhilosopherMapper::toDTO)
                .toList();
    }

    public List<WorkResponseDTO> getWorksByCountry(Long id) {
        Country country = countryRepository.findById(id)
                .orElseThrow(() -> new CountryNotFoundException("Country not Found"));

        List<Work> workList = workRepository.findByCountryId(country.getId());

        return workList.stream()
                .map(WorkMapper::toDTO)
                .toList();
    }

    public Page<CountryResponseDTO> searchCountries(String name, Pageable pageable) {
        Specification<Country> spec = CountrySpecification.hasName(name);

        return countryRepository.findAll(spec, pageable).map(CountryMapper::toDTO);
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
