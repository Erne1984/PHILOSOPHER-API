package com.floriano.philosophy_api.controllers.ThemeController;

import com.floriano.philosophy_api.dto.ThemeDTO.ThemeRequestDTO;
import com.floriano.philosophy_api.dto.ThemeDTO.ThemeResponseDTO;
import com.floriano.philosophy_api.mapper.ThemeMapper;
import com.floriano.philosophy_api.model.Theme.Theme;
import com.floriano.philosophy_api.payload.ApiResponse;
import com.floriano.philosophy_api.services.ThemeService.ThemeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("themes")
public class ThemeController {

    private final ThemeService themeService;

    public ThemeController(ThemeService themeService) {
        this.themeService = themeService;
    }


    @PostMapping
    public ResponseEntity<ApiResponse<ThemeResponseDTO>> createTheme(@RequestBody ThemeRequestDTO dto) {

        Theme created = themeService.createTheme(dto);
        ThemeResponseDTO response = ThemeMapper.toDTO(created);

        return  new ResponseEntity<>(new ApiResponse<>(true, "Tema criado com sucesso!", response), HttpStatus.CREATED);
    }
}
