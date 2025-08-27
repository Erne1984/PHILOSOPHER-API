package com.floriano.philosophy_api.specification;

import com.floriano.philosophy_api.model.Work.Work;
import org.springframework.data.jpa.domain.Specification;

public class WorkSpecification {

    public static Specification<Work> hasTitle(String name) {
        return (root, query, criteriaBuilder) ->
                name == null ? null : criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("title")),
                        "%" + name.toLowerCase() + "%"
                );
    }

    public static Specification<Work> hasPhilosopherName(String philosopherName) {
        return (root, query, criteriaBuilder) ->
                philosopherName == null ? null :
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("philosopher").get("name")),
                                "%" + philosopherName.toLowerCase() + "%"
                        );
    }

    public static Specification<Work> yearGreaterThanOrEqualTo(Integer startYear) {
        return (root, query, criteriaBuilder) ->
                startYear == null ? null :
                        criteriaBuilder.greaterThanOrEqualTo(root.get("year"), startYear);
    }

    public static Specification<Work> yearLessThanOrEqualTo(Integer endYear) {
        return (root, query, criteriaBuilder) ->
                endYear == null ? null :
                        criteriaBuilder.lessThanOrEqualTo(root.get("year"), endYear);
    }

}
