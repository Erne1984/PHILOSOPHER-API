package com.floriano.philosophy_api.specification;

import com.floriano.philosophy_api.model.SchoolOfThought.SchoolOfThought;
import org.springframework.data.jpa.domain.Specification;

public class SchoolOfThoughtSpecification {

    public static Specification<SchoolOfThought> hasName(String name) {
        return (root, query, criteriaBuilder) ->
                name == null ? null : criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }
}
