package com.floriano.philosophy_api.controllers.InfluenceController;

import com.floriano.philosophy_api.dto.InfluenceDTO.InfluenceRequestDTO;
import com.floriano.philosophy_api.dto.InfluenceDTO.InfluenceResponseDTO;
import com.floriano.philosophy_api.mapper.InfluenceMapper;
import com.floriano.philosophy_api.model.Influence.Influence;
import com.floriano.philosophy_api.payload.ApiResponse;
import com.floriano.philosophy_api.services.InfluenceService.InfluenceService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/influences")
@Hidden
public class InfluenceController {

    private final InfluenceService influenceService;

    public InfluenceController(InfluenceService influenceService) {
        this.influenceService = influenceService;
    }

    @Operation(summary = "Get all influences", description = "Returns all registered influences in the system")
    @GetMapping
    public ResponseEntity<ApiResponse<List<InfluenceResponseDTO>>> getInfluences() {
        List<InfluenceResponseDTO> influenceResponseDTOS = influenceService.getInfluences();
        return ResponseEntity.ok(new ApiResponse<>(true, "Influences retrieved successfully", influenceResponseDTOS));
    }

    @Operation(summary = "Get influence by ID", description = "Returns detailed information about an influence by its ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<InfluenceResponseDTO>> getInfluenceById(@PathVariable Long id) {
        InfluenceResponseDTO influenceResponseDTO = influenceService.getInfluenceById(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Influence found", influenceResponseDTO));
    }

    @Operation(summary = "Create influence", description = "Adds a new influence to the system")
    @PostMapping
    public ResponseEntity<ApiResponse<InfluenceResponseDTO>> createInfluence(@RequestBody InfluenceRequestDTO dto) {
        Influence created = influenceService.createInfluence(dto);
        InfluenceResponseDTO response = InfluenceMapper.toDTO(created);
        return new ResponseEntity<>(
                new ApiResponse<>(true, "Influence created successfully", response),
                HttpStatus.CREATED
        );
    }

    @Operation(summary = "Update influence", description = "Updates the details of an existing influence by its ID")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<InfluenceResponseDTO>> updateInfluence(@PathVariable Long id, @RequestBody InfluenceRequestDTO dto) {
        Influence updated = influenceService.updateInfluence(id, dto);
        InfluenceResponseDTO responseDTO = InfluenceMapper.toDTO(updated);
        return new ResponseEntity<>(
                new ApiResponse<>(true, "Influence updated successfully", responseDTO),
                HttpStatus.OK
        );
    }

    @Operation(summary = "Delete influence", description = "Removes an existing influence from the system by its ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<InfluenceResponseDTO>> deleteInfluence(@PathVariable Long id) {
        Influence deleted = influenceService.deleteInfluence(id);
        InfluenceResponseDTO responseDTO = InfluenceMapper.toDTO(deleted);
        return new ResponseEntity<>(
                new ApiResponse<>(true, "Influence with id " + id + " deleted successfully", responseDTO),
                HttpStatus.OK
        );
    }
}
