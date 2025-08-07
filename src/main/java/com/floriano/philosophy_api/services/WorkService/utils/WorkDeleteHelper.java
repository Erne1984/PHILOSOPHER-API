package com.floriano.philosophy_api.services.WorkService.utils;

import com.floriano.philosophy_api.model.Work.Work;

public class WorkDeleteHelper {

    public static void detachAllRelationShips(Work work) {
        work.clearThemes();
        work.clearSchoolOfThoughts();
    }
}
