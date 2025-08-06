package com.floriano.philosophy_api.services.QuoteService.utils;

import com.floriano.philosophy_api.model.Quote.Quote;

public class QuoteDeleteHelper {

    public static void detachAllRelationships(Quote quote) {
        quote.clearThemes();

        if (quote.getPhilosopher() != null) {
            quote.getPhilosopher().getQuotes().remove(quote);
        }
        if (quote.getWork() != null) {
            quote.getWork().getQuotes().remove(quote);
        }

        quote.setPhilosopher(null);
        quote.setWork(null);
    }
}
