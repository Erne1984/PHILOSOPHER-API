package com.floriano.philosophy_api.specification;

import com.floriano.philosophy_api.model.Philosopher.Philosopher;
import org.springframework.data.jpa.domain.Specification;

public class PhilosopherSpecification {

    public static Specification<Philosopher> hasName(String name) {
        return (root, query, criteriaBuilder) ->
                name == null ? null : criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Philosopher> hasCountryName(String countryName) {
        return (root, query, criteriaBuilder) ->
                countryName == null ? null : criteriaBuilder.like(criteriaBuilder.lower(root.get("country").get("name")), "%" + countryName.toLowerCase() + "%");
    }

    public static Specification<Philosopher> hasSchoolName(String schoolName) {
        return (root, query, criteriaBuilder) -> {
            if (schoolName == null) return null;
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.join("schoolOfThoughts").get("name")),
                    "%" + schoolName.toLowerCase() + "%"
            );
        };
    }

    public static Specification<Philosopher> bornGreaterThanOrEqualTo(Integer startYear) {
        return ((root, query, criteriaBuilder) ->
                startYear == null ? null :
                criteriaBuilder.greaterThanOrEqualTo(root.get("birthYear"), startYear));
    }

    public static Specification<Philosopher> bornBeforeThanOrEqualTo(Integer endYear) {
        return ((root, query, criteriaBuilder) ->
                endYear == null ? null :
                        criteriaBuilder.lessThanOrEqualTo(root.get("birthYear"), endYear));
    }
}
