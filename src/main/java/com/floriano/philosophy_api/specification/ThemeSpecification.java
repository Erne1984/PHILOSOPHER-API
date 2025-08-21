package com.floriano.philosophy_api.specification;

import com.floriano.philosophy_api.model.Theme.Theme;
import org.springframework.data.jpa.domain.Specification;

public class ThemeSpecification {

    public static Specification<Theme> hasName(String name) {
        return (root, query, criteriaBuilder) ->
                name == null ? null : criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }
}
