package com.floriano.philosophy_api.services.ThemeService.utils;

import com.floriano.philosophy_api.model.Theme.Theme;

public class ThemeDeleteHelper {

    public static void detachAllRelationships(Theme theme) {
        theme.clearPhilosophers();
        theme.clearWorks();
        theme.clearQuotes();
    }
}
