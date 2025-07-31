package com.floriano.philosophy_api.dto.InfluenceDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InfluenceRequestDTO {

    private Long influencerId;

    private Long influencedId;

    private String strength;
}
