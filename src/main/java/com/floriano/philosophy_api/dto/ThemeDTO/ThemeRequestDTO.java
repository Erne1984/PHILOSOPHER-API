package com.floriano.philosophy_api.dto.ThemeDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ThemeRequestDTO {

    private String name;
    private String description;
    private List<Long> philosophersIds;
    private List<Long> worksIds;
    private List<Long> quotesIds;
}
