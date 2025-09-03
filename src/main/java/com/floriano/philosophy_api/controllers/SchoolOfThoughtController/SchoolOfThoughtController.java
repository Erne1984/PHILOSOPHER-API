package com.floriano.philosophy_api.controllers.SchoolOfThoughtController;

import com.floriano.philosophy_api.dto.PageDTO.PageDTO;
import com.floriano.philosophy_api.dto.PhilosopherDTO.PhilosopherResponseDTO;
import com.floriano.philosophy_api.dto.SchoolOfThoughtDTO.SchoolOfThoughtRequestDTO;
import com.floriano.philosophy_api.dto.SchoolOfThoughtDTO.SchoolOfThoughtResponseDTO;
import com.floriano.philosophy_api.dto.WorkDTO.WorkResponseDTO;
import com.floriano.philosophy_api.mapper.PageMapper;
import com.floriano.philosophy_api.mapper.PhilosopherMapper;
import com.floriano.philosophy_api.mapper.SchoolOfThoughtMapper;
import com.floriano.philosophy_api.mapper.WorkMapper;
import com.floriano.philosophy_api.model.Philosopher.Philosopher;
import com.floriano.philosophy_api.model.SchoolOfThought.SchoolOfThought;
import com.floriano.philosophy_api.model.Work.Work;
import com.floriano.philosophy_api.payload.ApiResponse;
import com.floriano.philosophy_api.payload.ResponseFactory;
import com.floriano.philosophy_api.services.SchoolOfThoughtService.SchoolOfThoughtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/schools")
@Tag(name = "SchoolsOfThought")
public class SchoolOfThoughtController {

    private final SchoolOfThoughtService schoolOfThoughtService;

    public SchoolOfThoughtController(SchoolOfThoughtService schoolOfThoughtService) {
        this.schoolOfThoughtService = schoolOfThoughtService;
    }

    @Operation(summary = "Get all schools of thought", description = "Returns a paginated list of all schools of thought in the system")
    @GetMapping
    public ResponseEntity<ApiResponse<PageDTO<SchoolOfThoughtResponseDTO>>> getSchoolsOfThought(@RequestParam(defaultValue = "0") int page,
                                                                                                @RequestParam(defaultValue = "10") int size,
                                                                                                @RequestParam(defaultValue = "id") String sortBy,
                                                                                                @RequestParam(defaultValue = "asc") String order
    ) {
        Sort.Direction direction = order.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<SchoolOfThoughtResponseDTO> schoolsPage = schoolOfThoughtService.getSchoolOfThoughts(pageable);
        return ResponseFactory.ok("Schools list retrieved successfully", PageMapper.toDTO(schoolsPage));
    }

    @Operation(summary = "Get school of thought by ID", description = "Returns detailed information of a specific school of thought by its ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SchoolOfThoughtResponseDTO>> getSchoolOfThought(@PathVariable Long id) {
        SchoolOfThoughtResponseDTO responseDTO = schoolOfThoughtService.getSchoolOfThought(id);
        return ResponseFactory.ok("School found", responseDTO);
    }

    @Operation(summary = "Get philosophers by school", description = "Returns philosophers associated with a specific school of thought (paginated)")
    @GetMapping("/{id}/philosophers")
    public ResponseEntity<ApiResponse<PageDTO<PhilosopherResponseDTO>>> getPhilosophersBySchool(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String order) {

        Sort.Direction direction = order.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<PhilosopherResponseDTO> philosophersPage = schoolOfThoughtService.getPhilosophersBySchool(id, pageable);

        String message = "Philosophers from school retrieved successfully";
        return ResponseFactory.ok(message, PageMapper.toDTO(philosophersPage));
    }

    @Operation(summary = "Get works by school", description = "Returns works associated with a specific school of thought (paginated)")
    @GetMapping("/{id}/works")
    public ResponseEntity<ApiResponse<PageDTO<WorkResponseDTO>>> getWorksBySchool(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String order) {

        Sort.Direction direction = order.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<WorkResponseDTO> worksPage = schoolOfThoughtService.getWorksBySchool(id, pageable);

        return ResponseFactory.ok("Works from school retrieved successfully", PageMapper.toDTO(worksPage));
    }

    @Operation(summary = "Search schools of thought", description = "Searches schools of thought by title")
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<PageDTO<SchoolOfThoughtResponseDTO>>> searchSchoolsOfThought(
            @RequestParam(required = false) String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String order
    ) {
        Sort.Direction direction = order.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<SchoolOfThoughtResponseDTO> schoolsPage = schoolOfThoughtService.searchSchoolOfThought(title, pageable);
        return ResponseFactory.ok("Schools of thought searched successfully", PageMapper.toDTO(schoolsPage));
    }

    @Operation(summary = "Associate philosopher to school", description = "Adds a philosopher to an existing school of thought", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/{schoolId}/philosophers/{philosopherId}")
    public ResponseEntity<ApiResponse<Void>> addPhilosopherToSchool(@PathVariable Long schoolId, @PathVariable Long philosopherId) {
        schoolOfThoughtService.addPhilosopherToSchool(schoolId, philosopherId);
        return ResponseFactory.ok("Philosopher associated to school successfully", null);
    }

    @Operation(summary = "Remove philosopher from school", description = "Removes a philosopher from an existing school of thought", security = @SecurityRequirement(name = "bearerAuth"))
    @DeleteMapping("/{schoolId}/philosophers/{philosopherId}")
    public ResponseEntity<ApiResponse<Void>> removePhilosopherFromSchool(@PathVariable Long schoolId, @PathVariable Long philosopherId) {
        schoolOfThoughtService.removePhilosopherFromSchool(schoolId, philosopherId);
        return ResponseFactory.ok("Philosopher removed from school successfully", null);
    }

    @Operation(summary = "Associate work to school", description = "Adds a work to an existing school of thought", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/{schoolId}/works/{workId}")
    public ResponseEntity<ApiResponse<Void>> addWorkToSchool(@PathVariable Long schoolId, @PathVariable Long workId) {
        schoolOfThoughtService.addWorkToSchool(schoolId, workId);
        return ResponseFactory.ok("Work associated to school successfully", null);
    }

    @Operation(summary = "Remove work from school", description = "Removes a work from an existing school of thought", security = @SecurityRequirement(name = "bearerAuth"))
    @DeleteMapping("/{schoolId}/works/{workId}")
    public ResponseEntity<ApiResponse<Void>> removeWorkFromSchool(@PathVariable Long schoolId, @PathVariable Long workId) {
        schoolOfThoughtService.removeWorkFromSchool(schoolId, workId);
        return ResponseFactory.ok("Work removed from school successfully", null);
    }

    @Operation(summary = "Create school of thought", description = "Adds a new school of thought to the system", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping
    public ResponseEntity<ApiResponse<SchoolOfThoughtResponseDTO>> createSchoolOfThought(@RequestBody SchoolOfThoughtRequestDTO dto) {
        SchoolOfThought created = schoolOfThoughtService.createSchoolOfThought(dto);
        SchoolOfThoughtResponseDTO responseDTO = SchoolOfThoughtMapper.toDTO(created);
        return ResponseFactory.created("School of thought created successfully", responseDTO);
    }

    @Operation(summary = "Update school of thought", description = "Updates an existing school of thought by its ID", security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SchoolOfThoughtResponseDTO>> updateSchoolOfThought(@PathVariable Long id, @RequestBody SchoolOfThoughtRequestDTO dto) {
        SchoolOfThought updated = schoolOfThoughtService.updateSchoolOfThought(id, dto);
        SchoolOfThoughtResponseDTO response = SchoolOfThoughtMapper.toDTO(updated);
        return ResponseFactory.ok("School of thought updated successfully", response);
    }

    @Operation(summary = "Delete school of thought", description = "Removes a school of thought from the system by its ID", security = @SecurityRequirement(name = "bearerAuth"))
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<SchoolOfThoughtResponseDTO>> deleteSchoolOfThought(@PathVariable Long id) {
        SchoolOfThought deleted = schoolOfThoughtService.deleteSchoolOfThought(id);
        SchoolOfThoughtResponseDTO responseDTO = SchoolOfThoughtMapper.toDTO(deleted);
        return ResponseFactory.ok("School of thought with id " + id + " deleted successfully", responseDTO);
    }
}
