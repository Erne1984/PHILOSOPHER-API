package com.floriano.philosophy_api.specification;

import com.floriano.philosophy_api.model.Quote.Quote;
import com.floriano.philosophy_api.model.SchoolOfThought.SchoolOfThought;
import org.springframework.data.jpa.domain.Specification;

public class QuoteSpecification {

    public static Specification<Quote> hasContent(String content) {
        return (root, query, criteriaBuilder) ->
                content == null ? null : criteriaBuilder.like(criteriaBuilder.lower(root.get("content")), "%" + content.toLowerCase() + "%");
    }
}
