package com.floriano.philosophy_api.controllers.PhilosopherController;

import com.floriano.philosophy_api.dto.PhilosopherDTO.PhilosopherRequestDTO;
import com.floriano.philosophy_api.dto.PhilosopherDTO.PhilosopherResponseDTO;
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

    @PostMapping
    public ResponseEntity<ApiResponse<PhilosopherResponseDTO>> createPhilosopher(
            @RequestBody PhilosopherRequestDTO dto) {

        Philosopher created = philosopherService.createPhilosopher(dto);
        PhilosopherResponseDTO responseDto = PhilosopherMapper.toDTO(created);
        return new ResponseEntity<>(new ApiResponse<>(true, "Filósofo criado com sucesso", responseDto), HttpStatus.CREATED);
    }

}
