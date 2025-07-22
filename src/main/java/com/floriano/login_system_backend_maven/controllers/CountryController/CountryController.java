package com.floriano.login_system_backend_maven.controllers.CountryController;

import com.floriano.login_system_backend_maven.model.Country.Country;
import com.floriano.login_system_backend_maven.services.CountryService.CountryService;
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
    public Country createCountry(@RequestBody Country c) {
        return  countryService.createCountry(c);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCountryById(@PathVariable Long id) {
            if (countryService.deleteCountryById(id)) {
                return new ResponseEntity<>("Entidade deletada", HttpStatus.OK);
            }

            return new ResponseEntity<>("Entidade não deletada", HttpStatus.BAD_REQUEST);
    }
}
