package com.floriano.philosophy_api.controllers.ThemeController;

import com.floriano.philosophy_api.dto.ThemeDTO.ThemeRequestDTO;
import com.floriano.philosophy_api.dto.ThemeDTO.ThemeResponseDTO;
import com.floriano.philosophy_api.mapper.ThemeMapper;
import com.floriano.philosophy_api.model.Theme.Theme;
import com.floriano.philosophy_api.payload.ApiResponse;
import com.floriano.philosophy_api.services.ThemeService.ThemeService;
import io.swagger.v3.oas.annotations.Operation;
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
    public ResponseEntity<ApiResponse<List<ThemeResponseDTO>>> getThemes() {
        List<ThemeResponseDTO> themes = themeService.getAllThemes();
        return ResponseEntity.ok(new ApiResponse<>(true, "Lista de temas", themes));
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
