package com.floriano.philosophy_api.controllers.PhilosopherController;

import com.floriano.philosophy_api.dto.PhilosopherDTO.PhilosopherRequestDTO;
import com.floriano.philosophy_api.dto.PhilosopherDTO.PhilosopherResponseDTO;
import com.floriano.philosophy_api.dto.QuoteDTO.QuoteResponseDTO;
import com.floriano.philosophy_api.mapper.PhilosopherMapper;
import com.floriano.philosophy_api.model.Philosopher.Philosopher;
import com.floriano.philosophy_api.payload.ApiResponse;
import com.floriano.philosophy_api.services.PhilosopherService.PhilosopherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("philosophers")
public class PhilosopherController {

    private final PhilosopherService philosopherService;

    public PhilosopherController(PhilosopherService philosopherService) {
        this.philosopherService = philosopherService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PhilosopherResponseDTO>>> getPhilosophers() {
        List<Philosopher> list = philosopherService.getAllPhilosophers();
        List<PhilosopherResponseDTO> dtoList = list.stream()
                .map(PhilosopherMapper::toDTO)
                .toList();

        return ResponseEntity.ok(new ApiResponse<>(true, "Lista de filósofos", dtoList));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PhilosopherResponseDTO>> getPhilosopherById(@PathVariable Long id) {
        Philosopher philosopher = philosopherService.getPhilosopherById(id);
        PhilosopherResponseDTO dto = PhilosopherMapper.toDTO(philosopher);
        return ResponseEntity.ok(new ApiResponse<>(true, "Filósofo encontrado", dto));
    }

    @GetMapping("/{id}/quotes")
    public ResponseEntity<ApiResponse<List<QuoteResponseDTO>>> getQuotesByPhilosopher(@PathVariable Long id) {

        List<QuoteResponseDTO> responseDTOS = philosopherService.getQuotesByPhilosopher(id);

        if (responseDTOS.isEmpty()) {
            return new ResponseEntity<>(new ApiResponse<>(true, "Quotes not found", List.of()), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ApiResponse<>(true, "Quotes found ", responseDTOS), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PhilosopherResponseDTO>> createPhilosopher(
            @RequestBody PhilosopherRequestDTO dto) {

        Philosopher created = philosopherService.createPhilosopher(dto);
        PhilosopherResponseDTO responseDto = PhilosopherMapper.toDTO(created);
        return new ResponseEntity<>(new ApiResponse<>(true, "Filósofo criado com sucesso", responseDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PhilosopherResponseDTO>> updatePhilosopher(@PathVariable Long id, @RequestBody PhilosopherRequestDTO dto) {

        Philosopher updated = philosopherService.updatePhilosopher(id, dto);
        PhilosopherResponseDTO responseDTO = PhilosopherMapper.toDTO(updated);


        return  new ResponseEntity<>(new ApiResponse<>(true, "Philosopher updated successfully!!", responseDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<PhilosopherResponseDTO>> deletePhilosopher(@PathVariable Long id) {

        Philosopher deleted = philosopherService.deletePhilosopher(id);
        PhilosopherResponseDTO responseDTO = PhilosopherMapper.toDTO(deleted);


        return  new ResponseEntity<>(new ApiResponse<>(true, "Philosopher deleted successfully!!", responseDTO), HttpStatus.OK);
    }
}
