package com.floriano.philosophy_api.controllers.PhilosopherController;

import com.floriano.philosophy_api.dto.InfluenceDTO.InfluenceResponseDTO;
import com.floriano.philosophy_api.dto.PageDTO.PageDTO;
import com.floriano.philosophy_api.dto.PhilosopherDTO.PhilosopherRequestDTO;
import com.floriano.philosophy_api.dto.PhilosopherDTO.PhilosopherResponseDTO;
import com.floriano.philosophy_api.dto.QuoteDTO.QuoteResponseDTO;
import com.floriano.philosophy_api.dto.WorkDTO.WorkResponseDTO;
import com.floriano.philosophy_api.mapper.PageMapper;
import com.floriano.philosophy_api.mapper.PhilosopherMapper;
import com.floriano.philosophy_api.model.Influence.Influence;
import com.floriano.philosophy_api.model.Influence.InfluenceStrength;
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

    @Operation(summary = "Get all philosophers", description = "Returns the complete list of registered philosophers")
    @GetMapping
    public ResponseEntity<ApiResponse<List<PhilosopherResponseDTO>>> getPhilosophers() {
        List<Philosopher> list = philosopherService.getAllPhilosophers();
        List<PhilosopherResponseDTO> dtoList = list.stream()
                .map(PhilosopherMapper::toDTO)
                .toList();

        return ResponseEntity.ok(new ApiResponse<>(true, "Philosophers retrieved successfully", dtoList));
    }

    @Operation(summary = "Get philosopher by ID", description = "Returns detailed information of a philosopher by their ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PhilosopherResponseDTO>> getPhilosopherById(@PathVariable Long id) {
        Philosopher philosopher = philosopherService.getPhilosopherById(id);
        PhilosopherResponseDTO dto = PhilosopherMapper.toDTO(philosopher);
        return ResponseEntity.ok(new ApiResponse<>(true, "Philosopher found", dto));
    }

    @Operation(summary = "Get quotes by philosopher", description = "Returns all quotes attributed to a specific philosopher")
    @GetMapping("/{id}/quotes")
    public ResponseEntity<ApiResponse<List<QuoteResponseDTO>>> getQuotesByPhilosopher(@PathVariable Long id) {
        List<QuoteResponseDTO> responseDTOS = philosopherService.getQuotesByPhilosopher(id);

        if (responseDTOS.isEmpty()) {
            return ResponseEntity.ok(new ApiResponse<>(true, "No quotes found", List.of()));
        }
        return ResponseEntity.ok(new ApiResponse<>(true, "Quotes of " + responseDTOS.get(0).getPhilosopherName() + " found", responseDTOS));
    }

    @Operation(summary = "Get works by philosopher", description = "Returns all works written by a specific philosopher")
    @GetMapping("/{id}/works")
    public ResponseEntity<ApiResponse<List<WorkResponseDTO>>> getWorksByPhilosopher(@PathVariable Long id) {
        List<WorkResponseDTO> workResponseDTOS = philosopherService.getWorksByPhilosopher(id);

        if (workResponseDTOS.isEmpty()) {
            return ResponseEntity.ok(new ApiResponse<>(true, "No works found for this philosopher", List.of()));
        }
        return ResponseEntity.ok(new ApiResponse<>(true, "Works of " + workResponseDTOS.get(0).getPhilosopherName() + " found", workResponseDTOS));
    }

    @Operation(summary = "Get influenced philosophers", description = "Returns all philosophers influenced by a specific philosopher")
    @GetMapping("/{id}/influenced")
    public ResponseEntity<ApiResponse<List<InfluenceResponseDTO>>> getInfluenced(@PathVariable Long id) {
        List<InfluenceResponseDTO> list = philosopherService.getInfluencedByPhilosopher(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Influenced philosophers retrieved successfully", list));
    }

    @Operation(summary = "Get influencers of a philosopher", description = "Returns all philosophers who influenced a specific philosopher")
    @GetMapping("/{id}/influencers")
    public ResponseEntity<ApiResponse<List<InfluenceResponseDTO>>> getInfluencers(@PathVariable Long id) {
        List<InfluenceResponseDTO> list = philosopherService.getInfluencersOfPhilosopher(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Influencers retrieved successfully", list));
    }

    @Operation(summary = "Get random philosopher", description = "Returns detailed information of a random philosopher")
    @GetMapping("/random")
    public ResponseEntity<ApiResponse<PhilosopherResponseDTO>> getRandomPhilosopher() {
        PhilosopherResponseDTO philosopherResponseDTO = philosopherService.getRandomPhilosopher();
        return ResponseEntity.ok(new ApiResponse<>(true, "Random philosopher", philosopherResponseDTO));
    }

    @Operation(summary = "Search philosophers", description = "Searches for philosophers by name, country, school, or birth year range")
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<PageDTO<PhilosopherResponseDTO>>> searchPhilosophers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String countryName,
            @RequestParam(required = false) String schoolName,
            @RequestParam(required = false) Integer startYear,
            @RequestParam(required = false) Integer endYear,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String order
    ) {
        Sort.Direction direction = order.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<PhilosopherResponseDTO> philosophersPage = philosopherService.searchPhilosophers(name, countryName, schoolName, startYear, endYear, pageable);

        return ResponseFactory.ok("Philosophers retrieved successfully", PageMapper.toDTO(philosophersPage));
    }

    @Operation(summary = "Add theme to philosopher", description = "Associates a theme to a philosopher")
    @PostMapping("/{philosopherId}/themes/{themeId}")
    public ResponseEntity<ApiResponse<Void>> addThemeToPhilosopher(
            @PathVariable Long philosopherId,
            @PathVariable Long themeId
    ) {
        philosopherService.addThemeToPhilosopher(philosopherId, themeId);
        return ResponseFactory.ok("Theme added to philosopher successfully", null);
    }

    @Operation(summary = "Remove theme from philosopher", description = "Removes the association between a theme and a philosopher")
    @DeleteMapping("/{philosopherId}/themes/{themeId}")
    public ResponseEntity<ApiResponse<Void>> removeThemeFromPhilosopher(
            @PathVariable Long philosopherId,
            @PathVariable Long themeId
    ) {
        philosopherService.removeThemeFromPhilosopher(philosopherId, themeId);
        return ResponseFactory.ok("Theme removed from philosopher successfully", null);
    }

    @Operation(summary = "Add influence", description = "Adds a new influence relationship between two philosophers")
    @PostMapping("/{influencerId}/influences/{influencedId}")
    public InfluenceResponseDTO addInfluence(@PathVariable Long influencerId,
                                             @PathVariable Long influencedId,
                                             @RequestParam InfluenceStrength strength) {
        Influence influence = philosopherService.addInfluence(influencerId, influencedId, strength);
        return new InfluenceResponseDTO(
                influence.getId(),
                influence.getInfluencer().getName(),
                influence.getInfluenced().getName(),
                influence.getStrength().name()
        );
    }

    @Operation(summary = "Remove influence", description = "Removes an influence relationship by its ID")
    @DeleteMapping("/influences/{influenceId}")
    public void removeInfluence(@PathVariable Long influenceId) {
        philosopherService.removeInfluence(influenceId);
    }

    @Operation(summary = "Create philosopher", description = "Adds a new philosopher to the system")
    @PostMapping
    public ResponseEntity<ApiResponse<PhilosopherResponseDTO>> createPhilosopher(
            @RequestBody PhilosopherRequestDTO dto) {

        Philosopher created = philosopherService.createPhilosopher(dto);
        PhilosopherResponseDTO responseDto = PhilosopherMapper.toDTO(created);
        return new ResponseEntity<>(new ApiResponse<>(true, "Philosopher created successfully", responseDto), HttpStatus.CREATED);
    }

    @Operation(summary = "Update philosopher", description = "Updates an existing philosopher by ID")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PhilosopherResponseDTO>> updatePhilosopher(@PathVariable Long id, @RequestBody PhilosopherRequestDTO dto) {
        Philosopher updated = philosopherService.updatePhilosopher(id, dto);
        PhilosopherResponseDTO responseDTO = PhilosopherMapper.toDTO(updated);
        return new ResponseEntity<>(new ApiResponse<>(true, "Philosopher updated successfully", responseDTO), HttpStatus.OK);
    }

    @Operation(summary = "Delete philosopher", description = "Removes a philosopher from the system by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<PhilosopherResponseDTO>> deletePhilosopher(@PathVariable Long id) {
        Philosopher deleted = philosopherService.deletePhilosopher(id);
        PhilosopherResponseDTO responseDTO = PhilosopherMapper.toDTO(deleted);
        return new ResponseEntity<>(new ApiResponse<>(true, "Philosopher deleted successfully", responseDTO), HttpStatus.OK);
    }
}
