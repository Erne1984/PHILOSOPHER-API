package com.floriano.philosophy_api.controllers.SchoolOfThoughtController;

import com.floriano.philosophy_api.dto.PageDTO.PageDTO;
import com.floriano.philosophy_api.dto.SchoolOfThoughtDTO.SchoolOfThoughtRequestDTO;
import com.floriano.philosophy_api.dto.SchoolOfThoughtDTO.SchoolOfThoughtResponseDTO;
import com.floriano.philosophy_api.dto.WorkDTO.WorkResponseDTO;
import com.floriano.philosophy_api.mapper.PageMapper;
import com.floriano.philosophy_api.mapper.SchoolOfThoughtMapper;
import com.floriano.philosophy_api.model.Philosopher.Philosopher;
import com.floriano.philosophy_api.model.SchoolOfThought.SchoolOfThought;
import com.floriano.philosophy_api.model.Work.Work;
import com.floriano.philosophy_api.payload.ApiResponse;
import com.floriano.philosophy_api.payload.ResponseFactory;
import com.floriano.philosophy_api.services.SchoolOfThoughtService.SchoolOfThoughtService;
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
@RequestMapping("/v1/schools")
public class SchoolOfThoughtController {

    private final SchoolOfThoughtService schoolOfThoughtService;

    public SchoolOfThoughtController(SchoolOfThoughtService schoolOfThoughtService) {
        this.schoolOfThoughtService = schoolOfThoughtService;
    }

    @Operation(summary = "Listar escolas filosóficas", description = "Retorna todas as escolas de pensamento registradas no sistema")
    @GetMapping
    public ResponseEntity<ApiResponse<List<SchoolOfThoughtResponseDTO>>> getSchoolsOfThought() {
        List<SchoolOfThoughtResponseDTO> dtoList = schoolOfThoughtService.getSchoolOfThoughts();
        return ResponseFactory.ok("Schools list", dtoList);
    }

    @Operation(summary = "Buscar escola filosófica por ID", description = "Retorna os detalhes de uma escola de pensamento pelo seu ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SchoolOfThoughtResponseDTO>> getSchoolOfThought(@PathVariable Long id) {
        SchoolOfThoughtResponseDTO schoolOfThoughtResponseDTO = schoolOfThoughtService.getSchoolOfThought(id);
        return ResponseFactory.ok("School found", schoolOfThoughtResponseDTO);
    }

    @Operation(summary = "Listar filósofos de uma escola", description = "Retorna todos os filósofos associados a uma escola de pensamento")
    @GetMapping("/{id}/philosophers")
    public ResponseEntity<ApiResponse<List<Philosopher>>> getPhilosophersBySchool(@PathVariable Long id) {
        List<Philosopher> philosophers = schoolOfThoughtService.getPhilosophersBySchool(id);
        return ResponseFactory.ok("Philosophers from school", philosophers);
    }

    @Operation(summary = "Listar obras de uma escola", description = "Retorna todas as obras associadas a uma escola de pensamento")
    @GetMapping("/{id}/works")
    public ResponseEntity<ApiResponse<List<Work>>> getWorksBySchool(@PathVariable Long id) {
        List<Work> works = schoolOfThoughtService.getWorksBySchool(id);
        return ResponseFactory.ok("Works from school", works);
    }

    @Operation(summary = "Listar obras de uma escola", description = "Retorna todas as obras associadas a uma escola de pensamento")
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<PageDTO<SchoolOfThoughtResponseDTO>>> getWorksBySchool(@RequestParam(required = false) String title,
                                                                                             @RequestParam(defaultValue = "0") int page,
                                                                                             @RequestParam(defaultValue = "10") int size,
                                                                                             @RequestParam(defaultValue = "id") String sortBy,
                                                                                             @RequestParam(defaultValue = "asc") String order) {
        Sort.Direction direction = order.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<SchoolOfThoughtResponseDTO> worksPage = schoolOfThoughtService.searchSchoolOfThought(title, pageable);

        return ResponseFactory.ok("School of Thought searched", PageMapper.toDTO(worksPage));
    }

    @Operation(summary = "Associar filósofo a uma escola", description = "Adiciona um filósofo a uma escola de pensamento existente")
    @PostMapping("/{schoolId}/philosophers/{philosopherId}")
    public ResponseEntity<ApiResponse<Void>> addPhilosopherToSchool(@PathVariable Long schoolId, @PathVariable Long philosopherId) {
        schoolOfThoughtService.addPhilosopherToSchool(schoolId, philosopherId);
        return ResponseFactory.ok("Philosopher associated to school successfully", null);
    }

    @Operation(summary = "Desassociar filósofo de uma escola", description = "Remove um filósofo de uma escola de pensamento existente")
    @DeleteMapping("/{schoolId}/philosophers/{philosopherId}")
    public ResponseEntity<ApiResponse<Void>> removePhilosopherFromSchool(@PathVariable Long schoolId, @PathVariable Long philosopherId) {
        schoolOfThoughtService.removePhilosopherFromSchool(schoolId, philosopherId);
        return ResponseFactory.ok("Philosopher removed from school successfully", null);
    }

    @Operation(summary = "Associar obra a uma escola", description = "Adiciona uma obra a uma escola de pensamento existente")
    @PostMapping("/{schoolId}/works/{workId}")
    public ResponseEntity<ApiResponse<Void>> addWorkToSchool(@PathVariable Long schoolId, @PathVariable Long workId) {
        schoolOfThoughtService.addWorkToSchool(schoolId, workId);
        return ResponseFactory.ok("Work associated to school successfully", null);
    }

    @Operation(summary = "Desassociar obra de uma escola", description = "Remove uma obra de uma escola de pensamento existente")
    @DeleteMapping("/{schoolId}/works/{workId}")
    public ResponseEntity<ApiResponse<Void>> removeWorkFromSchool(@PathVariable Long schoolId, @PathVariable Long workId) {
        schoolOfThoughtService.removeWorkFromSchool(schoolId, workId);
        return ResponseFactory.ok("Work removed from school successfully", null);
    }

    @Operation(summary = "Criar escola filosófica", description = "Adiciona uma nova escola de pensamento ao sistema")
    @PostMapping
    public ResponseEntity<ApiResponse<SchoolOfThoughtResponseDTO>> createSchoolOfThought(@RequestBody SchoolOfThoughtRequestDTO dto) {
        SchoolOfThought created = schoolOfThoughtService.createSchoolOfThought(dto);
        SchoolOfThoughtResponseDTO responseDTO = SchoolOfThoughtMapper.toDTO(created);
        return ResponseFactory.created("Escola filosófica criada com sucesso", responseDTO);
    }

    @Operation(summary = "Atualizar escola filosófica", description = "Atualiza os dados de uma escola de pensamento existente pelo seu ID")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SchoolOfThoughtResponseDTO>> updateSchoolOfThought(@PathVariable Long id, @RequestBody SchoolOfThoughtRequestDTO dto) {
        SchoolOfThought schoolOfThought = schoolOfThoughtService.updateSchoolOfThought(id, dto);
        SchoolOfThoughtResponseDTO response = SchoolOfThoughtMapper.toDTO(schoolOfThought);
        return ResponseFactory.ok("School of thought updated successfully!", response);
    }

    @Operation(summary = "Deletar escola filosófica", description = "Remove uma escola de pensamento do sistema pelo seu ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<SchoolOfThoughtResponseDTO>> deleteSchoolOfThought(@PathVariable Long id) {
        SchoolOfThought schoolOfThought = schoolOfThoughtService.deleteSchoolOfThought(id);
        SchoolOfThoughtResponseDTO responseDTO = SchoolOfThoughtMapper.toDTO(schoolOfThought);
        return ResponseFactory.ok("School of thought with id " + id + " successfully deleted!", responseDTO);
    }
}