package com.floriano.philosophy_api.controllers.CountryController;

import com.floriano.philosophy_api.dto.CountryDTO.CountryRequestDTO;
import com.floriano.philosophy_api.dto.CountryDTO.CountryResponseDTO;
import com.floriano.philosophy_api.exceptions.ResourceNotFoundException;
import com.floriano.philosophy_api.mapper.CountryMapper;
import com.floriano.philosophy_api.model.Country.Country;
import com.floriano.philosophy_api.payload.ApiResponse;
import com.floriano.philosophy_api.services.CountryService.CountryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("countries")
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CountryResponseDTO>>> getAllCountries() {
        List<Country> countries = countryService.getCountry();

        List<CountryResponseDTO> dtoList = countries.stream()
                .map(CountryMapper::toDTO)
                .toList();

        return ResponseEntity.ok(new ApiResponse<>(true, "Countries list", dtoList));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CountryResponseDTO>> createCountry(@RequestBody CountryRequestDTO dto) {
        Country created = countryService.createCountry(dto);
        CountryResponseDTO responseDTO = CountryMapper.toDTO(created);

        return new ResponseEntity<>(
                new ApiResponse<>(true, "País criado com sucesso", responseDTO),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CountryResponseDTO>> updateCountry(
            @PathVariable Long id,
            @RequestBody CountryRequestDTO dto
    ) {
        Country updated = countryService.updateCountry(id, dto);
        CountryResponseDTO responseDTO = CountryMapper.toDTO(updated);

        return new ResponseEntity<>(
                new ApiResponse<>(true, "Country updated successfully!", responseDTO),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<CountryResponseDTO>> deleteCountryById(@PathVariable Long id) {
        Country deleted = countryService.deleteCountryById(id);
        CountryResponseDTO responseDTO = CountryMapper.toDTO(deleted);

        return new ResponseEntity<>(
                new ApiResponse<>(true, "Country with id " + id + " deleted successfully!", responseDTO),
                HttpStatus.OK
        );
    }
}
