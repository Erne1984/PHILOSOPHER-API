package com.floriano.philosophy_api.controllers.InfluenceController;

import com.floriano.philosophy_api.dto.InfluenceDTO.InfluenceRequestDTO;
import com.floriano.philosophy_api.dto.InfluenceDTO.InfluenceResponseDTO;
import com.floriano.philosophy_api.mapper.InfluenceMapper;
import com.floriano.philosophy_api.model.Influence.Influence;
import com.floriano.philosophy_api.payload.ApiResponse;
import com.floriano.philosophy_api.services.InfluenceService.InfluenceService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "Listar influências", description = "Retorna todas as influências registradas no sistema")
    @GetMapping
    public ResponseEntity<ApiResponse<List<InfluenceResponseDTO>>> getInfluences() {
        List<InfluenceResponseDTO> influenceResponseDTOS = influenceService.getInfluences();
        return ResponseEntity.ok(new ApiResponse<>(true, "Influences list", influenceResponseDTOS));
    }

    @Operation(summary = "Buscar influência por ID", description = "Retorna os detalhes de uma influência pelo seu ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<InfluenceResponseDTO>> getInfluenceById(@PathVariable Long id) {
        InfluenceResponseDTO influenceResponseDTO = influenceService.getInfluenceById(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Influence found", influenceResponseDTO));
    }

    @Operation(summary = "Criar influência", description = "Adiciona uma nova influência ao sistema")
    @PostMapping
    public ResponseEntity<ApiResponse<InfluenceResponseDTO>> createInfluence(@RequestBody InfluenceRequestDTO dto) {
        Influence created = influenceService.createInfluence(dto);
        InfluenceResponseDTO response = InfluenceMapper.toDTO(created);
        return new ResponseEntity<>(new ApiResponse<>(true, "Influência criada com sucesso!", response), HttpStatus.CREATED);
    }

    @Operation(summary = "Atualizar influência", description = "Atualiza os dados de uma influência existente pelo seu ID")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<InfluenceResponseDTO>> updateInfluence(@PathVariable Long id, @RequestBody InfluenceRequestDTO dto) {
        Influence updated = influenceService.updateInfluence(id, dto);
        InfluenceResponseDTO responseDTO = InfluenceMapper.toDTO(updated);
        return new ResponseEntity<>(new ApiResponse<>(true, "Influence updated successfully!", responseDTO), HttpStatus.OK);
    }

    @Operation(summary = "Deletar influência", description = "Remove uma influência do sistema pelo seu ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<InfluenceResponseDTO>> deleteInfluence(@PathVariable Long id) {
        Influence deleted = influenceService.deleteInfluence(id);
        InfluenceResponseDTO responseDTO = InfluenceMapper.toDTO(deleted);
        return new ResponseEntity<>(new ApiResponse<>(true, "Influence with id " + id + " deleted successfully!", responseDTO), HttpStatus.OK);
    }
}
