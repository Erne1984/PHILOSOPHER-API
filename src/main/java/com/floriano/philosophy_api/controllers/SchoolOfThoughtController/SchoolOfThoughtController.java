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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("schools")
public class SchoolOfThoughtController {

    private final SchoolOfThoughtService schoolOfThoughtService;

    public SchoolOfThoughtController(SchoolOfThoughtService schoolOfThoughtService) {
        this.schoolOfThoughtService = schoolOfThoughtService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<SchoolOfThoughtResponseDTO>>> getSchoolOfThought() {
        List<SchoolOfThoughtResponseDTO> dtoList = schoolOfThoughtService.getSchoolOfThoughts();

        return ResponseEntity.ok(new ApiResponse<>(true, "Schools List", dtoList));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<SchoolOfThoughtResponseDTO>> createSchoolOfThought(@RequestBody SchoolOfThoughtRequestDTO dto) {
        SchoolOfThought created = schoolOfThoughtService.createSchoolOfThought(dto);

        SchoolOfThoughtResponseDTO responseDTO = SchoolOfThoughtMapper.toDTO(created);

        return new ResponseEntity<>(new ApiResponse<>(true, "Escola filosófica criada com sucesso", responseDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SchoolOfThoughtResponseDTO>> updateSchoolOfThought(@PathVariable Long id, @RequestBody SchoolOfThoughtRequestDTO dto) {
        SchoolOfThought schoolOfThought = schoolOfThoughtService.updateSchoolOfThought(id, dto);

        SchoolOfThoughtResponseDTO response = SchoolOfThoughtMapper.toDTO(schoolOfThought);

        return  new ResponseEntity<>(new ApiResponse<>(true, "School of thought updated successfully!", response), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<SchoolOfThoughtResponseDTO>> deleteSchoolOfThought(@PathVariable Long id) {
        SchoolOfThought schoolOfThought = schoolOfThoughtService.deleteSchoolOfThought(id);

        SchoolOfThoughtResponseDTO responseDTO = SchoolOfThoughtMapper.toDTO(schoolOfThought);

        return new ResponseEntity<>(new ApiResponse<>(true, "SchoolOfThought with id " + id + " successfully deleted!", responseDTO), HttpStatus.OK);
    }
}
