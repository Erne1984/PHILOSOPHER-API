package com.floriano.philosophy_api.controllers.QuoteController;

import com.floriano.philosophy_api.dto.QuoteDTO.QuoteRequestDTO;
import com.floriano.philosophy_api.dto.QuoteDTO.QuoteResponseDTO;
import com.floriano.philosophy_api.mapper.QuoteMapper;
import com.floriano.philosophy_api.model.Quote.Quote;
import com.floriano.philosophy_api.payload.ApiResponse;
import com.floriano.philosophy_api.services.QuoteService.QuoteService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quotes")
public class QuoteController {

    private final QuoteService quoteService;

    public QuoteController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    @Operation(summary = "Search all quotes", description = "Returns a list of all registered citations.")
    @GetMapping
    public ResponseEntity<ApiResponse<List<QuoteResponseDTO>>> getQuotes() {
        List<QuoteResponseDTO> dtoList = quoteService.getAllQuotes();

        return ResponseEntity.ok(new ApiResponse<>(true, "Quotes List", dtoList));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<QuoteResponseDTO>> createQuote(@RequestBody QuoteRequestDTO dto) {

        Quote created = quoteService.createQuote(dto);

        QuoteResponseDTO responseDTO = QuoteMapper.toDTO(created);

        return new ResponseEntity<>(new ApiResponse<>(true, "Citação criada com sucesso", responseDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<QuoteResponseDTO>> updateQuote(@PathVariable Long id, @RequestBody QuoteRequestDTO dto) {

        Quote updated = quoteService.updateQuote(id, dto);

        QuoteResponseDTO response = QuoteMapper.toDTO(updated);

        return  new ResponseEntity<>(new ApiResponse<>(true, "Citação atualizado com sucesso!", response), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteQuote(@PathVariable Long id){
        quoteService.deleteQuote(id);
        return new ResponseEntity<>(new ApiResponse<>(true, "Quote with id " + id + " successfully deleted!", null), HttpStatus.OK);
    }
}
