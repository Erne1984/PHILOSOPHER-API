package com.floriano.philosophy_api.controllers.InfluenceController;

import com.floriano.philosophy_api.dto.InfluenceDTO.InfluenceRequestDTO;
import com.floriano.philosophy_api.dto.InfluenceDTO.InfluenceResponseDTO;
import com.floriano.philosophy_api.mapper.InfluenceMapper;
import com.floriano.philosophy_api.model.Influence.Influence;
import com.floriano.philosophy_api.payload.ApiResponse;
import com.floriano.philosophy_api.services.InfluenceService.InfluenceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/influences")
public class InfluenceController {

    private final InfluenceService influenceService;

    public InfluenceController(InfluenceService influenceService) {
        this.influenceService = influenceService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<InfluenceResponseDTO>>> getInfluences() {

        List<Influence> influences = influenceService.getInfluences();

        List<InfluenceResponseDTO> influenceResponseDTOS = influences
                .stream()
                .map(InfluenceMapper::toDTO)
                .toList();

        return ResponseEntity.ok(new ApiResponse<>(true, "influences List", influenceResponseDTOS));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<InfluenceResponseDTO>> createInfluence(@RequestBody InfluenceRequestDTO dto) {

        Influence created = influenceService.createInfluence(dto);

        InfluenceResponseDTO response = InfluenceMapper.toDTO(created);

        return new ResponseEntity<>(new ApiResponse<>(true, "Influência criada com sucesso", response), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<InfluenceResponseDTO>> updateInfluence(@PathVariable Long id, @RequestBody InfluenceRequestDTO dto) {

        Influence updated = influenceService.updateInfluence(id, dto);

        InfluenceResponseDTO responseDTO = InfluenceMapper.toDTO(updated);

        return new ResponseEntity<>(new ApiResponse<>(true, "influence updated successfully!", responseDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteInfluence(@PathVariable Long id) {

        Influence deleted = influenceService.deleteInfluence(id);

        return  new ResponseEntity<>(new ApiResponse<>(true, "Influence with " + id + " deleted successfully!", null), HttpStatus.OK);
    }

}