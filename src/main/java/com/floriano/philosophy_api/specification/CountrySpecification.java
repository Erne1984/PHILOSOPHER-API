package com.floriano.philosophy_api.specification;

import com.floriano.philosophy_api.model.Country.Country;
import org.springframework.data.jpa.domain.Specification;

public class CountrySpecification {

    public static Specification<Country> hasName(String name) {
        return (root, query, criteriaBuilder) ->
                name == null ? null : criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }
}
