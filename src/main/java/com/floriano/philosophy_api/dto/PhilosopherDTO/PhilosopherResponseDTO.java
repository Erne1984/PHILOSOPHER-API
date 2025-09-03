package com.floriano.philosophy_api.dto.PhilosopherDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PhilosopherResponseDTO {

    private Long id;
    private String name;
    private String iepLink;
    private String speLink;
    private String img;
    private int birthYear;
    private int deathYear;
    private String bio;
    private String country;
    private List<String> schoolOfThoughtName;


}
