package com.floriano.philosophy_api.specification;

import com.floriano.philosophy_api.model.Philosopher.Philosopher;
import org.springframework.data.jpa.domain.Specification;

public class PhilosopherSpecification {

    public static Specification<Philosopher> hasName(String name) {
        return (root, query, criteriaBuilder) ->
                name == null ? null : criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }
}
