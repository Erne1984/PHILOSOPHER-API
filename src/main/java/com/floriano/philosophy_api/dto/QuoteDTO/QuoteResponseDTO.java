package com.floriano.philosophy_api.dto.QuoteDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuoteResponseDTO {

    private Long id;
    private String content;
    private String philosopherName;

    private String workTitle;

    private List<String> themesName;
}
