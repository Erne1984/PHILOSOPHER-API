package com.floriano.philosophy_api.controllers.PhilosopherController;

import com.floriano.philosophy_api.dto.InfluenceDTO.InfluenceResponseDTO;
import com.floriano.philosophy_api.dto.PageDTO.PageDTO;
import com.floriano.philosophy_api.dto.PhilosopherDTO.PhilosopherRequestDTO;
import com.floriano.philosophy_api.dto.PhilosopherDTO.PhilosopherResponseDTO;
import com.floriano.philosophy_api.dto.QuoteDTO.QuoteResponseDTO;
import com.floriano.philosophy_api.dto.ThemeDTO.ThemeResponseDTO;
import com.floriano.philosophy_api.dto.WorkDTO.WorkResponseDTO;
import com.floriano.philosophy_api.mapper.PageMapper;
import com.floriano.philosophy_api.mapper.PhilosopherMapper;
import com.floriano.philosophy_api.model.Philosopher.Philosopher;
import com.floriano.philosophy_api.payload.ApiResponse;
import com.floriano.philosophy_api.payload.ResponseFactory;
import com.floriano.philosophy_api.services.PhilosopherService.PhilosopherService;
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
@RequestMapping("/v1/philosophers")
public class PhilosopherController {

    private final PhilosopherService philosopherService;

    public PhilosopherController(PhilosopherService philosopherService) {
        this.philosopherService = philosopherService;
    }

    @Operation(summary = "Listar todos os filósofos", description = "Retorna a lista completa de filósofos cadastrados")
    @GetMapping
    public ResponseEntity<ApiResponse<List<PhilosopherResponseDTO>>> getPhilosophers() {
        List<Philosopher> list = philosopherService.getAllPhilosophers();
        List<PhilosopherResponseDTO> dtoList = list.stream()
                .map(PhilosopherMapper::toDTO)
                .toList();

        return ResponseEntity.ok(new ApiResponse<>(true, "Lista de filósofos", dtoList));
    }

    @Operation(summary = "Buscar filósofo por ID", description = "Retorna as informações detalhadas de um filósofo pelo seu ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PhilosopherResponseDTO>> getPhilosopherById(@PathVariable Long id) {
        Philosopher philosopher = philosopherService.getPhilosopherById(id);
        PhilosopherResponseDTO dto = PhilosopherMapper.toDTO(philosopher);
        return ResponseEntity.ok(new ApiResponse<>(true, "Filósofo encontrado", dto));
    }

    @Operation(summary = "Listar citações de um filósofo", description = "Retorna todas as citações atribuídas a um filósofo específico")
    @GetMapping("/{id}/quotes")
    public ResponseEntity<ApiResponse<List<QuoteResponseDTO>>> getQuotesByPhilosopher(@PathVariable Long id) {
        List<QuoteResponseDTO> responseDTOS = philosopherService.getQuotesByPhilosopher(id);

        if (responseDTOS.isEmpty()) {
            return new ResponseEntity<>(new ApiResponse<>(true, "Quotes not found", List.of()), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ApiResponse<>(true, "Quotes of " + responseDTOS.get(0).getPhilosopherName() +  " found", responseDTOS), HttpStatus.OK);
    }

    @Operation(summary = "Listar obras de um filósofo", description = "Retorna todas as obras escritas por um filósofo específico")
    @GetMapping("/{id}/works")
    public ResponseEntity<ApiResponse<List<WorkResponseDTO>>> getWorksByPhilosopher(@PathVariable Long id) {
        List<WorkResponseDTO> workResponseDTOS = philosopherService.getWorksByPhilosopher(id);

        if ( workResponseDTOS.isEmpty()) {
            return new ResponseEntity<>(new ApiResponse<>(true, "No works from this philosopher", List.of()), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ApiResponse<>(true, "Works of " + workResponseDTOS.get(0).getPhilosopherName() + " found", workResponseDTOS), HttpStatus.OK);
    }

    @Operation(summary = "Listar filósofos influenciados", description = "Retorna todos os filósofos que foram influenciados por um filósofo específico")
    @GetMapping("/{id}/influenced")
    public ResponseEntity<ApiResponse<List<InfluenceResponseDTO>>> getInfluenced(@PathVariable Long id) {
        List<InfluenceResponseDTO> list = philosopherService.getInfluencedByPhilosopher(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Influenced philosophers found", list));
    }

    @Operation(summary = "Listar influenciadores de um filósofo", description = "Retorna todos os filósofos que influenciaram um filósofo específico")
    @GetMapping("/{id}/influencers")
    public ResponseEntity<ApiResponse<List<InfluenceResponseDTO>>> getInfluencers(@PathVariable Long id) {
        List<InfluenceResponseDTO> list = philosopherService.getInfluencersOfPhilosopher(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Influencers found", list));
    }

    @Operation(summary = "Search Philosophers", description = "Searches for philosophers by name")
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<PageDTO<PhilosopherResponseDTO>>> searchPhilosophers(@RequestParam(required = false) String name,
                                                                               @RequestParam(defaultValue = "0") int page,
                                                                               @RequestParam(defaultValue = "10") int size,
                                                                               @RequestParam(defaultValue = "id") String sortBy,
                                                                               @RequestParam(defaultValue = "asc") String order)
    {
        Sort.Direction direction = order.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<PhilosopherResponseDTO> philosophersPage = philosopherService.searchPhilosophers(name, pageable);

        return ResponseFactory.ok("Philosophers searched", PageMapper.toDTO(philosophersPage));
    }

    @Operation(summary = "Criar filósofo", description = "Adiciona um novo filósofo ao sistema")
    @PostMapping
    public ResponseEntity<ApiResponse<PhilosopherResponseDTO>> createPhilosopher(
            @RequestBody PhilosopherRequestDTO dto) {

        Philosopher created = philosopherService.createPhilosopher(dto);
        PhilosopherResponseDTO responseDto = PhilosopherMapper.toDTO(created);
        return new ResponseEntity<>(new ApiResponse<>(true, "Filósofo criado com sucesso", responseDto), HttpStatus.CREATED);
    }

    @Operation(summary = "Atualizar filósofo", description = "Atualiza as informações de um filósofo existente pelo seu ID")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PhilosopherResponseDTO>> updatePhilosopher(@PathVariable Long id, @RequestBody PhilosopherRequestDTO dto) {
        Philosopher updated = philosopherService.updatePhilosopher(id, dto);
        PhilosopherResponseDTO responseDTO = PhilosopherMapper.toDTO(updated);
        return  new ResponseEntity<>(new ApiResponse<>(true, "Philosopher updated successfully!!", responseDTO), HttpStatus.OK);
    }

    @Operation(summary = "Deletar filósofo", description = "Remove um filósofo do sistema pelo seu ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<PhilosopherResponseDTO>> deletePhilosopher(@PathVariable Long id) {
        Philosopher deleted = philosopherService.deletePhilosopher(id);
        PhilosopherResponseDTO responseDTO = PhilosopherMapper.toDTO(deleted);
        return  new ResponseEntity<>(new ApiResponse<>(true, "Philosopher deleted successfully!!", responseDTO), HttpStatus.OK);
    }
}
