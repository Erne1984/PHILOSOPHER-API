package com.floriano.philosophy_api.specification;

import com.floriano.philosophy_api.model.Work.Work;
import org.springframework.data.jpa.domain.Specification;

public class WorkSpecification {

    public static Specification<Work> hasTitle(String name) {
        return (root, query, criteriaBuilder) ->
                name == null ? null : criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + name.toLowerCase() + "%");
    }
}
