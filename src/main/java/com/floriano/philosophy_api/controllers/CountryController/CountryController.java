package com.floriano.philosophy_api.controllers.CountryController;

import com.floriano.philosophy_api.dto.CountryDTO.CountryRequestDTO;
import com.floriano.philosophy_api.dto.CountryDTO.CountryResponseDTO;
import com.floriano.philosophy_api.dto.PageDTO.PageDTO;
import com.floriano.philosophy_api.dto.PhilosopherDTO.PhilosopherResponseDTO;
import com.floriano.philosophy_api.dto.WorkDTO.WorkResponseDTO;
import com.floriano.philosophy_api.mapper.CountryMapper;
import com.floriano.philosophy_api.mapper.PageMapper;
import com.floriano.philosophy_api.model.Country.Country;
import com.floriano.philosophy_api.payload.ApiResponse;
import com.floriano.philosophy_api.payload.ResponseFactory;
import com.floriano.philosophy_api.services.CountryService.CountryService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/countries")
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @Operation(summary = "Get all countries", description = "Returns the complete list of registered countries")
    @GetMapping
    public ResponseEntity<ApiResponse<List<CountryResponseDTO>>> getAllCountries() {
        List<CountryResponseDTO> countriesResponse = countryService.getCountries();
        return ResponseEntity.ok(new ApiResponse<>(true, "Countries retrieved successfully", countriesResponse));
    }

    @Operation(summary = "Get country by ID", description = "Returns detailed information about a country by its ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CountryResponseDTO>> getByIdCountry(@PathVariable Long id){
        CountryResponseDTO countryResponseDTO = countryService.getCountryById(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Country found", countryResponseDTO));
    }

    @Operation(summary = "Get philosophers by country", description = "Returns all philosophers associated with a specific country")
    @GetMapping("/{id}/philosophers")
    public ResponseEntity<ApiResponse<List<PhilosopherResponseDTO>>> getPhilosophersByCountry(@PathVariable Long id){
        List<PhilosopherResponseDTO> philosopherResponseDTOS = countryService.getPhilosophersByCountry(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Philosophers from country retrieved successfully", philosopherResponseDTOS));
    }

    @Operation(summary = "Get works by country", description = "Returns all works authored by philosophers of a given country")
    @GetMapping("/{id}/works")
    public ResponseEntity<ApiResponse<List<WorkResponseDTO>>> getWorksByCountry(@PathVariable Long id){
        List<WorkResponseDTO> workResponseDTOS = countryService.getWorksByCountry(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Works from country retrieved successfully", workResponseDTOS));
    }

    @Operation(summary = "Search countries", description = "Search for countries by name")
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<PageDTO<CountryResponseDTO>>> searchCountries(@RequestParam(required = false) String name,
                                                                                    @RequestParam(defaultValue = "0") int page,
                                                                                    @RequestParam(defaultValue = "10") int size,
                                                                                    @RequestParam(defaultValue = "id") String sortBy,
                                                                                    @RequestParam(defaultValue = "asc") String order) {
        Sort.Direction direction = order.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<CountryResponseDTO> countriesPage = countryService.searchCountries(name, pageable);

        return ResponseFactory.ok("Countries search executed successfully", PageMapper.toDTO(countriesPage));
    }

    @Operation(summary = "Create country", description = "Creates a new country in the system")
    @PostMapping
    public ResponseEntity<ApiResponse<CountryResponseDTO>> createCountry(@RequestBody CountryRequestDTO dto) {
        Country created = countryService.createCountry(dto);
        CountryResponseDTO responseDTO = CountryMapper.toDTO(created);
        return new ResponseEntity<>(
                new ApiResponse<>(true, "Country created successfully", responseDTO),
                HttpStatus.CREATED
        );
    }

    @Operation(summary = "Update country", description = "Updates the details of an existing country by its ID")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CountryResponseDTO>> updateCountry(
            @PathVariable Long id,
            @RequestBody CountryRequestDTO dto
    ) {
        Country updated = countryService.updateCountry(id, dto);
        CountryResponseDTO responseDTO = CountryMapper.toDTO(updated);
        return new ResponseEntity<>(
                new ApiResponse<>(true, "Country updated successfully", responseDTO),
                HttpStatus.OK
        );
    }

    @Operation(summary = "Delete country", description = "Deletes an existing country by its ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<CountryResponseDTO>> deleteCountryById(@PathVariable Long id) {
        Country deleted = countryService.deleteCountryById(id);
        CountryResponseDTO responseDTO = CountryMapper.toDTO(deleted);
        return new ResponseEntity<>(
                new ApiResponse<>(true, "Country with id " + id + " deleted successfully", responseDTO),
                HttpStatus.OK
        );
    }
}
