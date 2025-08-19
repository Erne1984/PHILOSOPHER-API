package com.floriano.philosophy_api.controllers.ThemeController;

import com.floriano.philosophy_api.dto.PageDTO.PageDTO;
import com.floriano.philosophy_api.dto.PhilosopherDTO.PhilosopherResponseDTO;
import com.floriano.philosophy_api.dto.QuoteDTO.QuoteResponseDTO;
import com.floriano.philosophy_api.dto.ThemeDTO.ThemeRequestDTO;
import com.floriano.philosophy_api.dto.ThemeDTO.ThemeResponseDTO;
import com.floriano.philosophy_api.dto.WorkDTO.WorkResponseDTO;
import com.floriano.philosophy_api.mapper.ThemeMapper;
import com.floriano.philosophy_api.model.Theme.Theme;
import com.floriano.philosophy_api.payload.ApiResponse;
import com.floriano.philosophy_api.services.ThemeService.ThemeService;
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
@RequestMapping("/v1/themes")
public class ThemeController {

    private final ThemeService themeService;

    public ThemeController(ThemeService themeService) {
        this.themeService = themeService;
    }

    @Operation(summary = "Listar todos os temas", description = "Retorna todos os temas registrados no sistema")
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
        PageDTO<ThemeResponseDTO> pageDTO = new PageDTO<>(
                themesPage.getContent(),
                themesPage.getNumber(),
                themesPage.getSize(),
                themesPage.getTotalElements(),
                themesPage.getTotalPages()
        );
        return new ResponseEntity<ApiResponse<PageDTO<ThemeResponseDTO>>>(
                new ApiResponse<>(true, "Themes list", pageDTO),
                HttpStatus.OK
        );
    }

    @Operation(summary = "Listar filósofos de um tema", description = "Retorna todos os filósofos associados a um tema")
    @GetMapping("/{id}/philosophers")
    public ResponseEntity<ApiResponse<PageDTO<PhilosopherResponseDTO>>> getPhilosophersByTheme(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PhilosopherResponseDTO> philosophersPage = themeService.getPhilosophersByTheme(id, pageable);
        PageDTO<PhilosopherResponseDTO> pageDTO = new PageDTO<>(
                philosophersPage.getContent(),
                philosophersPage.getNumber(),
                philosophersPage.getSize(),
                philosophersPage.getTotalElements(),
                philosophersPage.getTotalPages()
        );
        return ResponseEntity.ok(new ApiResponse<>(true, "Philosophers list", pageDTO));
    }

    @Operation(summary = "Listar obras de um tema", description = "Retorna todas as obras associadas a um tema")
    @GetMapping("/{id}/works")
    public ResponseEntity<ApiResponse<PageDTO<WorkResponseDTO>>> getWorksByTheme(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<WorkResponseDTO> worksPage = themeService.getWorksByTheme(id, pageable);
        PageDTO<WorkResponseDTO> pageDTO = new PageDTO<>(
                worksPage.getContent(),
                worksPage.getNumber(),
                worksPage.getSize(),
                worksPage.getTotalElements(),
                worksPage.getTotalPages()
        );
        return ResponseEntity.ok(new ApiResponse<>(true, "Works list", pageDTO));
    }

    @Operation(summary = "Listar citações de um tema", description = "Retorna todas as citações associadas a um tema")
    @GetMapping("/{id}/quotes")
    public ResponseEntity<ApiResponse<PageDTO<QuoteResponseDTO>>> getQuotesByTheme(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<QuoteResponseDTO> quotesPage = themeService.getQuotesByTheme(id, pageable);
        PageDTO<QuoteResponseDTO> pageDTO = new PageDTO<>(
                quotesPage.getContent(),
                quotesPage.getNumber(),
                quotesPage.getSize(),
                quotesPage.getTotalElements(),
                quotesPage.getTotalPages()
        );
        return ResponseEntity.ok(new ApiResponse<>(true, "Quotes list", pageDTO));
    }

    @Operation(summary = "Buscar tema por ID", description = "Retorna os detalhes de um tema pelo seu ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ThemeResponseDTO>> getThemeById(@PathVariable Long id) {
        ThemeResponseDTO theme = themeService.getThemeById(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Tema encontrado", theme));
    }

    @Operation(summary = "Criar tema", description = "Adiciona um novo tema ao sistema")
    @PostMapping
    public ResponseEntity<ApiResponse<ThemeResponseDTO>> createTheme(@RequestBody ThemeRequestDTO dto) {
        Theme created = themeService.createTheme(dto);
        ThemeResponseDTO response = ThemeMapper.toDTO(created);
        return new ResponseEntity<>(new ApiResponse<>(true, "Tema criado com sucesso!", response), HttpStatus.CREATED);
    }

    @Operation(summary = "Atualizar tema", description = "Atualiza os dados de um tema existente pelo seu ID")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ThemeResponseDTO>> updateTheme(@PathVariable Long id, @RequestBody ThemeRequestDTO dto) {
        Theme updated = themeService.updateTheme(id, dto);
        ThemeResponseDTO response = ThemeMapper.toDTO(updated);
        return new ResponseEntity<>(new ApiResponse<>(true, "Tema atualizado com sucesso!", response), HttpStatus.OK);
    }

    @Operation(summary = "Deletar tema", description = "Remove um tema do sistema pelo seu ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<ThemeResponseDTO>> deleteTheme(@PathVariable Long id) {
        Theme deleted = themeService.deleteThemeById(id);
        ThemeResponseDTO responseDTO = ThemeMapper.toDTO(deleted);
        return new ResponseEntity<>(new ApiResponse<>(true, "Tema com o id " + id + " deletado com sucesso!", responseDTO), HttpStatus.OK);
    }
}
