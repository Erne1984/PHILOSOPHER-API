package com.floriano.philosophy_api.controllers.WorkController;

import com.floriano.philosophy_api.dto.PageDTO.PageDTO;
import com.floriano.philosophy_api.dto.QuoteDTO.QuoteResponseDTO;
import com.floriano.philosophy_api.dto.ThemeDTO.ThemeResponseDTO;
import com.floriano.philosophy_api.dto.WorkDTO.WorkRequestDTO;
import com.floriano.philosophy_api.dto.WorkDTO.WorkResponseDTO;
import com.floriano.philosophy_api.mapper.PageMapper;
import com.floriano.philosophy_api.mapper.WorkMapper;
import com.floriano.philosophy_api.model.Work.Work;
import com.floriano.philosophy_api.payload.ApiResponse;
import com.floriano.philosophy_api.payload.ResponseFactory;
import com.floriano.philosophy_api.services.WorkService.WorkService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/works")
public class WorkController {

    private final WorkService workService;

    public WorkController(WorkService workService) {
        this.workService = workService;
    }

    @Operation(summary = "List all works", description = "Returns all works registered in the system")
    @GetMapping
    public ResponseEntity<ApiResponse<PageDTO<WorkResponseDTO>>> getAllWorks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String order
    ) {
        Sort.Direction direction = order.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<WorkResponseDTO> worksPage = workService.getAllWorks(pageable);

        return ResponseFactory.ok("Works list", PageMapper.toDTO(worksPage));
    }

    @Operation(summary = "Get work by ID", description = "Returns work details by its ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<WorkResponseDTO>> getWorkById(@PathVariable Long id) {
        WorkResponseDTO work = workService.getWorkById(id);
        return ResponseFactory.ok("Work found", work);
    }

    @Operation(summary = "List quotes by work", description = "Returns all quotes associated with a work")
    @GetMapping("/{id}/quotes")
    public ResponseEntity<ApiResponse<PageDTO<QuoteResponseDTO>>> getQuotesByWork(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<QuoteResponseDTO> quotesPage = workService.getQuotesByWork(id, pageable);

        return ResponseFactory.ok("Quotes list", PageMapper.toDTO(quotesPage));
    }

    @Operation(summary = "List themes by work", description = "Returns all themes associated with a work")
    @GetMapping("/{id}/themes")
    public ResponseEntity<ApiResponse<PageDTO<ThemeResponseDTO>>> getThemesByWork(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ThemeResponseDTO> themePage = workService.getThemesByWork(id, pageable);

        return ResponseFactory.ok("Theme list", PageMapper.toDTO(themePage));
    }

    @Operation(summary = "Search works", description = "Searches for works by title")
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<PageDTO<WorkResponseDTO>>> searchWorks(
            @RequestParam(required = false) String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String order
    ) {
        Sort.Direction direction = order.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<WorkResponseDTO> worksPage = workService.searchWorks(title, pageable);

        return ResponseFactory.ok("Works searched", PageMapper.toDTO(worksPage));
    }

    @Operation(summary = "Create work", description = "Adds a new work to the system")
    @PostMapping
    public ResponseEntity<ApiResponse<WorkResponseDTO>> createWork(@RequestBody WorkRequestDTO dto) {
        Work created = workService.createWork(dto);
        WorkResponseDTO response = WorkMapper.toDTO(created);
        return ResponseFactory.created("Work created successfully", response);
    }

    @Operation(summary = "Update work", description = "Updates an existing work by its ID")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<WorkResponseDTO>> updateWork(@PathVariable Long id, @RequestBody WorkRequestDTO dto) {
        Work updated = workService.updateWork(id, dto);
        WorkResponseDTO response = WorkMapper.toDTO(updated);
        return ResponseFactory.ok("Work updated successfully", response);
    }

    @Operation(summary = "Delete work", description = "Removes a work from the system by its ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<WorkResponseDTO>> deleteWork(@PathVariable Long id) {
        Work deleted = workService.deleteWork(id);
        WorkResponseDTO responseDTO = WorkMapper.toDTO(deleted);
        return ResponseFactory.ok("Work with id " + id + " deleted successfully!", responseDTO);
    }
}