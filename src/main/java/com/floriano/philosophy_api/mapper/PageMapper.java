package com.floriano.philosophy_api.mapper;

import com.floriano.philosophy_api.dto.PageDTO.PageDTO;
import org.springframework.data.domain.Page;

public class PageMapper {

    public static <T> PageDTO<T> toDTO(Page<T> page) {
        return new PageDTO<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }
}
