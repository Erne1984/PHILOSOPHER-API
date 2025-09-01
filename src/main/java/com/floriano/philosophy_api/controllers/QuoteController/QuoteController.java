package com.floriano.philosophy_api.controllers.QuoteController;

import com.floriano.philosophy_api.dto.PageDTO.PageDTO;
import com.floriano.philosophy_api.dto.QuoteDTO.QuoteRequestDTO;
import com.floriano.philosophy_api.dto.QuoteDTO.QuoteResponseDTO;
import com.floriano.philosophy_api.mapper.PageMapper;
import com.floriano.philosophy_api.mapper.QuoteMapper;
import com.floriano.philosophy_api.model.Quote.Quote;
import com.floriano.philosophy_api.payload.ApiResponse;
import com.floriano.philosophy_api.payload.ResponseFactory;
import com.floriano.philosophy_api.services.QuoteService.QuoteService;
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
@RequestMapping("/v1/quotes")
public class QuoteController {

    private final QuoteService quoteService;

    public QuoteController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    @Operation(summary = "Get all quotes", description = "Returns a paginated list of all registered quotes")
    @GetMapping
    public ResponseEntity<ApiResponse<PageDTO<QuoteResponseDTO>>> getQuotes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String order
    ) {
        Sort.Direction direction = order.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<QuoteResponseDTO> dtoList = quoteService.getAllQuotes(pageable);

        return ResponseFactory.ok("Quotes retrieved successfully", PageMapper.toDTO(dtoList));
    }

    @Operation(summary = "Get quote by ID", description = "Returns detailed information of a specific quote by its ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<QuoteResponseDTO>> getQuoteById(@PathVariable Long id) {
        QuoteResponseDTO quoteResponseDTO = quoteService.getQuoteById(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Quote found", quoteResponseDTO));
    }

    @Operation(summary = "Search quotes", description = "Searches for quotes by content")
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<PageDTO<QuoteResponseDTO>>> searchQuotes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) String content,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String order
    ) {
        Sort.Direction direction = order.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<QuoteResponseDTO> quotesPage = quoteService.searchQuotes(content, pageable);

        return ResponseFactory.ok("Quotes searched successfully", PageMapper.toDTO(quotesPage));
    }

    @Operation(summary = "Add theme to quote", description = "Associates a theme to a quote")
    @PostMapping("/{quoteId}/themes/{themeId}")
    public ResponseEntity<ApiResponse<Void>> addThemeToQuote(
            @PathVariable Long quoteId,
            @PathVariable Long themeId
    ) {
        quoteService.addThemeToQuote(quoteId, themeId);
        return ResponseFactory.ok("Theme added to quote successfully", null);
    }

    @Operation(summary = "Remove theme from quote", description = "Removes the association between a theme and a quote")
    @DeleteMapping("/{quoteId}/themes/{themeId}")
    public ResponseEntity<ApiResponse<Void>> removeThemeFromQuote(
            @PathVariable Long quoteId,
            @PathVariable Long themeId
    ) {
        quoteService.removeThemeFromQuote(quoteId, themeId);
        return ResponseFactory.ok("Theme removed from quote successfully", null);
    }

    @Operation(summary = "Create quote", description = "Adds a new quote to the system")
    @PostMapping
    public ResponseEntity<ApiResponse<QuoteResponseDTO>> createQuote(@RequestBody QuoteRequestDTO dto) {
        Quote created = quoteService.createQuote(dto);
        QuoteResponseDTO responseDTO = QuoteMapper.toDTO(created);
        return new ResponseEntity<>(new ApiResponse<>(true, "Quote created successfully", responseDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "Update quote", description = "Updates an existing quote by its ID")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<QuoteResponseDTO>> updateQuote(@PathVariable Long id, @RequestBody QuoteRequestDTO dto) {
        Quote updated = quoteService.updateQuote(id, dto);
        QuoteResponseDTO response = QuoteMapper.toDTO(updated);
        return new ResponseEntity<>(new ApiResponse<>(true, "Quote updated successfully", response), HttpStatus.OK);
    }

    @Operation(summary = "Delete quote", description = "Removes a quote from the system by its ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteQuote(@PathVariable Long id){
        quoteService.deleteQuote(id);
        return new ResponseEntity<>(new ApiResponse<>(true, "Quote with id " + id + " deleted successfully", null), HttpStatus.OK);
    }
}
