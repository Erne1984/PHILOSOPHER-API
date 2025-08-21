package com.floriano.philosophy_api.controllers.ThemeController;

import com.floriano.philosophy_api.dto.PageDTO.PageDTO;
import com.floriano.philosophy_api.dto.PhilosopherDTO.PhilosopherResponseDTO;
import com.floriano.philosophy_api.dto.QuoteDTO.QuoteResponseDTO;
import com.floriano.philosophy_api.dto.ThemeDTO.ThemeRequestDTO;
import com.floriano.philosophy_api.dto.ThemeDTO.ThemeResponseDTO;
import com.floriano.philosophy_api.dto.WorkDTO.WorkResponseDTO;
import com.floriano.philosophy_api.mapper.PageMapper;
import com.floriano.philosophy_api.mapper.ThemeMapper;
import com.floriano.philosophy_api.model.Theme.Theme;
import com.floriano.philosophy_api.payload.ApiResponse;
import com.floriano.philosophy_api.services.ThemeService.ThemeService;
import com.floriano.philosophy_api.payload.ResponseFactory;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/themes")
public class ThemeController {

    private final ThemeService themeService;

    public ThemeController(ThemeService themeService) {
        this.themeService = themeService;
    }

    @Operation(summary = "List all themes", description = "Returns all themes registered in the system")
    @GetMapping
    public ResponseEntity<ApiResponse<PageDTO<ThemeResponseDTO>>> getAllThemes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String order
    ) {
        Sort.Direction direction = order.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<ThemeResponseDTO> themesPage = themeService.getAllThemes(pageable);

        return ResponseFactory.ok("Themes list", PageMapper.toDTO(themesPage));
    }

    @Operation(summary = "List philosophers by theme", description = "Returns all philosophers associated with a theme")
    @GetMapping("/{id}/philosophers")
    public ResponseEntity<ApiResponse<PageDTO<PhilosopherResponseDTO>>> getPhilosophersByTheme(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PhilosopherResponseDTO> philosophersPage = themeService.getPhilosophersByTheme(id, pageable);

        return ResponseFactory.ok("Philosophers list", PageMapper.toDTO(philosophersPage));
    }

    @Operation(summary = "List works by theme", description = "Returns all works associated with a theme")
    @GetMapping("/{id}/works")
    public ResponseEntity<ApiResponse<PageDTO<WorkResponseDTO>>> getWorksByTheme(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<WorkResponseDTO> worksPage = themeService.getWorksByTheme(id, pageable);

        return ResponseFactory.ok("Works list", PageMapper.toDTO(worksPage));
    }

    @Operation(summary = "List quotes by theme", description = "Returns all quotes associated with a theme")
    @GetMapping("/{id}/quotes")
    public ResponseEntity<ApiResponse<PageDTO<QuoteResponseDTO>>> getQuotesByTheme(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<QuoteResponseDTO> quotesPage = themeService.getQuotesByTheme(id, pageable);

        return ResponseFactory.ok("Quotes list", PageMapper.toDTO(quotesPage));
    }

    @Operation(summary = "Get theme by ID", description = "Returns theme details by its ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ThemeResponseDTO>> getThemeById(@PathVariable Long id) {
        ThemeResponseDTO theme = themeService.getThemeById(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Tema encontrado", theme));
    }

    @Operation(summary = "Search themes", description = "Searches for themes by name")
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<PageDTO<ThemeResponseDTO>>> searchThemes(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String order
    ) {
        Sort.Direction direction = order.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<ThemeResponseDTO> themesPage = themeService.searchThemes(name, pageable);

        return ResponseFactory.ok("Themes searched", PageMapper.toDTO(themesPage));
    }

    @Operation(summary = "Create theme", description = "Adds a new theme to the system")
    @PostMapping
    public ResponseEntity<ApiResponse<ThemeResponseDTO>> createTheme(@RequestBody ThemeRequestDTO dto) {
        Theme created = themeService.createTheme(dto);
        ThemeResponseDTO response = ThemeMapper.toDTO(created);
        return ResponseFactory.ok("Themes created successfully", response);
    }

    @Operation(summary = "Update theme", description = "Updates an existing theme by its ID")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ThemeResponseDTO>> updateTheme(@PathVariable Long id, @RequestBody ThemeRequestDTO dto) {
        Theme updated = themeService.updateTheme(id, dto);
        ThemeResponseDTO response = ThemeMapper.toDTO(updated);
        return ResponseFactory.ok("Themes updated successfully", response);
    }

    @Operation(summary = "Delete theme", description = "Removes a theme from the system by its ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<ThemeResponseDTO>> deleteTheme(@PathVariable Long id) {
        Theme deleted = themeService.deleteThemeById(id);
        ThemeResponseDTO responseDTO = ThemeMapper.toDTO(deleted);
        return ResponseFactory.ok("Theme with id " + id + " deleted successfully!", responseDTO);
    }
}