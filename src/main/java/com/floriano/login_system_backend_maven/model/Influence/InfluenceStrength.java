package com.floriano.login_system_backend_maven.model.Influence;

public enum InfluenceStrength {
    WEAK("small", 1),
    MEDIUM("medium", 2),
    STRONG("strong", 3);

    private final String description ;
    private final int level;

    private InfluenceStrength(String description , int level) {
        this.description  = description;
        this.level = level;
    }

    public String getDescription() {
        return description ;
    }

    public int getStrengthLevel() {
        return level;
    }
}
