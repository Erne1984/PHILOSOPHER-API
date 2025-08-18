package com.floriano.philosophy_api.controllers.CountryController;

import com.floriano.philosophy_api.dto.CountryDTO.CountryRequestDTO;
import com.floriano.philosophy_api.dto.CountryDTO.CountryResponseDTO;
import com.floriano.philosophy_api.dto.PhilosopherDTO.PhilosopherResponseDTO;
import com.floriano.philosophy_api.dto.WorkDTO.WorkResponseDTO;
import com.floriano.philosophy_api.mapper.CountryMapper;
import com.floriano.philosophy_api.model.Country.Country;
import com.floriano.philosophy_api.payload.ApiResponse;
import com.floriano.philosophy_api.services.CountryService.CountryService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "Listar todos os países", description = "Retorna a lista completa de países cadastrados")
    @GetMapping
    public ResponseEntity<ApiResponse<List<CountryResponseDTO>>> getAllCountries() {
        List<CountryResponseDTO> countriesResponse = countryService.getCountries();
        return ResponseEntity.ok(new ApiResponse<>(true, "Countries list", countriesResponse));
    }

    @Operation(summary = "Buscar país por ID", description = "Retorna as informações detalhadas de um país pelo seu ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CountryResponseDTO>> getByIdCountry(@PathVariable Long id){
        CountryResponseDTO countryResponseDTO = countryService.getCountryById(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Country found", countryResponseDTO));
    }

    @Operation(summary = "Listar filósofos por país", description = "Retorna todos os filósofos associados a um país específico")
    @GetMapping("/{id}/philosophers")
    public ResponseEntity<ApiResponse<List<PhilosopherResponseDTO>>> getPhilosophersByCountry(@PathVariable Long id){
        List<PhilosopherResponseDTO> philosopherResponseDTOS = countryService.getPhilosophersByCountry(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Philosophers of " + philosopherResponseDTOS.get(0).getCountry() + " found", philosopherResponseDTOS));
    }

    @Operation(summary = "Listar obras por país", description = "Retorna todas as obras de filósofos de um determinado país")
    @GetMapping("/{id}/works")
    public ResponseEntity<ApiResponse<List<WorkResponseDTO>>> getWorksByCountry(@PathVariable Long id){
        List<WorkResponseDTO> workResponseDTOS = countryService.getWorksByCountry(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Works of " + workResponseDTOS.get(0).getCountryName() + " found", workResponseDTOS));
    }

    @Operation(summary = "Criar país", description = "Cria um novo país no sistema")
    @PostMapping
    public ResponseEntity<ApiResponse<CountryResponseDTO>> createCountry(@RequestBody CountryRequestDTO dto) {
        Country created = countryService.createCountry(dto);
        CountryResponseDTO responseDTO = CountryMapper.toDTO(created);
        return new ResponseEntity<>(
                new ApiResponse<>(true, "País criado com sucesso", responseDTO),
                HttpStatus.CREATED
        );
    }

    @Operation(summary = "Atualizar país", description = "Atualiza as informações de um país existente pelo seu ID")
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

    @Operation(summary = "Deletar país", description = "Remove um país existente pelo seu ID")
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
