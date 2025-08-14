package com.floriano.philosophy_api.services.InfluenceService.utils;

import com.floriano.philosophy_api.model.Influence.Influence;
import com.floriano.philosophy_api.model.Philosopher.Philosopher;

public class InfluenceDeleteHelper {

    public static void detachAllRelationships(Influence influence) {
        if (influence.getInfluencer() != null) {
            Philosopher influencer = influence.getInfluencer();
            influencer.getInfluenced().remove(influence);
            influence.setInfluencer(null);
        }

        if (influence.getInfluenced() != null) {
            Philosopher influenced = influence.getInfluenced();
            influenced.getInfluencedBy().remove(influence);
            influence.setInfluenced(null);
        }
    }
}
