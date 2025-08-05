package com.floriano.philosophy_api.controllers.ThemeController;

import com.floriano.philosophy_api.dto.ThemeDTO.ThemeRequestDTO;
import com.floriano.philosophy_api.dto.ThemeDTO.ThemeResponseDTO;
import com.floriano.philosophy_api.mapper.PhilosopherMapper;
import com.floriano.philosophy_api.mapper.ThemeMapper;
import com.floriano.philosophy_api.model.Theme.Theme;
import com.floriano.philosophy_api.payload.ApiResponse;
import com.floriano.philosophy_api.services.ThemeService.ThemeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("themes")
public class ThemeController {

    private final ThemeService themeService;

    public ThemeController(ThemeService themeService) {
        this.themeService = themeService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ThemeResponseDTO>>> getThemes() {
        List<Theme> themes = themeService.getAllThemes();

        List<ThemeResponseDTO> dtoList = themes.stream()
                .map(ThemeMapper::toDTO)
                .toList();

        return ResponseEntity.ok(new ApiResponse<>(true, "Lista de temas", dtoList));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ThemeResponseDTO>> createTheme(@RequestBody ThemeRequestDTO dto) {

        Theme created = themeService.createTheme(dto);
        ThemeResponseDTO response = ThemeMapper.toDTO(created);

        return  new ResponseEntity<>(new ApiResponse<>(true, "Tema criado com sucesso!", response), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ThemeResponseDTO>> updateTheme(@PathVariable Long id, @RequestBody ThemeRequestDTO dto) {

        Theme updated = themeService.updateTheme(id, dto);
        ThemeResponseDTO response = ThemeMapper.toDTO(updated);

        return  new ResponseEntity<>(new ApiResponse<>(true, "Tema atualizado com sucesso!", response), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<ThemeResponseDTO>> deleteTheme(@PathVariable Long id) {

        Theme deleted = themeService.deleteThemeById(id);
        ThemeResponseDTO responseDTO = ThemeMapper.toDTO(deleted);

        return  new ResponseEntity<>(new ApiResponse<>(true, "Tema com o id " + id + " deleteado com sucesso!", responseDTO), HttpStatus.OK);
    }
}
