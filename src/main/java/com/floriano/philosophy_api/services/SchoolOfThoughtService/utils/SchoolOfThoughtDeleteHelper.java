package com.floriano.philosophy_api.services.SchoolOfThoughtService.utils;

import com.floriano.philosophy_api.model.SchoolOfThought.SchoolOfThought;

public class SchoolOfThoughtDeleteHelper {

    public static void detachAllRelationShips(SchoolOfThought schoolOfThought) {
        schoolOfThought.clearPhilosophers();
        schoolOfThought.clearWorks();
    }
}
