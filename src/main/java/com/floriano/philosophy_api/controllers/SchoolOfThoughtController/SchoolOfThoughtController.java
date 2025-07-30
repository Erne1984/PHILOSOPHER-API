package com.floriano.philosophy_api.controllers.SchoolOfThoughtController;

import com.floriano.philosophy_api.dto.PhilosopherDTO.PhilosopherResponseDTO;
import com.floriano.philosophy_api.dto.SchoolOfThoughtDTO.SchoolOfThoughtRequestDTO;
import com.floriano.philosophy_api.dto.SchoolOfThoughtDTO.SchoolOfThoughtResponseDTO;
import com.floriano.philosophy_api.mapper.SchoolOfThoughtMapper;
import com.floriano.philosophy_api.model.SchoolOfThought.SchoolOfThought;
import com.floriano.philosophy_api.payload.ApiResponse;
import com.floriano.philosophy_api.services.SchoolOfThoughtService.SchoolOfThoughtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("schools")
public class SchoolOfThoughtController {

    private final SchoolOfThoughtService schoolOfThoughtService;

    public SchoolOfThoughtController(SchoolOfThoughtService schoolOfThoughtService) {
        this.schoolOfThoughtService = schoolOfThoughtService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<SchoolOfThoughtResponseDTO>> createSchoolOfThought(@RequestBody SchoolOfThoughtRequestDTO dto) {

        SchoolOfThought created = schoolOfThoughtService.createSchoolOfThought(dto);

        SchoolOfThoughtResponseDTO responseDTO = SchoolOfThoughtMapper.toDTO(created);

        return new ResponseEntity<>(new ApiResponse<>(true, "Escola filosófica criada com sucesso", responseDTO), HttpStatus.CREATED);
    }
}
