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
public class QuoteRequestDTO {

    private String content;
    private Long philosopherId;
    private Long workId;
    private List<Long> themesId;
}
