package com.floriano.philosophy_api.controllers.InfluenceController;

import com.floriano.philosophy_api.dto.InfluenceDTO.InfluenceRequestDTO;
import com.floriano.philosophy_api.dto.InfluenceDTO.InfluenceResponseDTO;
import com.floriano.philosophy_api.mapper.InfluenceMapper;
import com.floriano.philosophy_api.model.Influence.Influence;
import com.floriano.philosophy_api.payload.ApiResponse;
import com.floriano.philosophy_api.services.InfluenceService.InfluenceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("influences")
public class InfluenceController {

    private final InfluenceService influenceService;

    public InfluenceController(InfluenceService influenceService) {
        this.influenceService = influenceService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<InfluenceResponseDTO>> createInfluence(@RequestBody InfluenceRequestDTO dto) {

        Influence created = influenceService.createInfluence(dto);

        InfluenceResponseDTO response = InfluenceMapper.toDTO(created);

        return new ResponseEntity<>(new ApiResponse<>(true, "Influência criada com sucesso", response), HttpStatus.CREATED);
    }
}
