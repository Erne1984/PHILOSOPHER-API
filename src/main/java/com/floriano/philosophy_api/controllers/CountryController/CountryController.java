package com.floriano.philosophy_api.controllers.CountryController;

import com.floriano.philosophy_api.exceptions.ResourceNotFoundException;
import com.floriano.philosophy_api.model.Country.Country;
import com.floriano.philosophy_api.payload.ApiResponse;
import com.floriano.philosophy_api.services.CountryService.CountryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("countries")
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }


    @PostMapping
    public ResponseEntity<ApiResponse<Country>> createCountry(@RequestBody Country c) {
        Country created = countryService.createCountry(c);
        return new ResponseEntity<>(new ApiResponse<>(true, "País criado com sucesso", created), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteCountryById(@PathVariable Long id) {
        boolean deleted = countryService.deleteCountryById(id);
        if (deleted) {
            return new ResponseEntity<>(new ApiResponse<>(true, "País deletado com sucesso", null), HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("País com id " + id + " não encontrado.");
        }
    }
}
