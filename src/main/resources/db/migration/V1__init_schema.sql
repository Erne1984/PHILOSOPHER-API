-- ============================
-- SCHEMA INIT (Flyway V1)
-- ============================

-- ============================
-- USERS
-- ============================
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    email TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL,
    role TEXT NOT NULL);

-- ============================
-- COUNTRIES
-- ============================
CREATE TABLE countries (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    start_year INT,
    end_year INT,
    region VARCHAR(255),
    continent VARCHAR(255),
    CONSTRAINT check_country_years CHECK (end_year IS NULL OR start_year IS NULL OR end_year >= start_year)
);

-- ============================
-- PHILOSOPHERS
-- ============================
CREATE TABLE philosophers (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    birth_year INT,
    death_year INT,
    iep_link VARCHAR(500),
    spe_link VARCHAR(500),
    img VARCHAR(500),
    bio TEXT,
    era VARCHAR(255),
    main_ideas TEXT,
    country_id BIGINT,
    CONSTRAINT fk_philosopher_country FOREIGN KEY (country_id) REFERENCES countries(id) ON DELETE SET NULL,
    CONSTRAINT check_philosopher_years CHECK (death_year IS NULL OR birth_year IS NULL OR death_year >= birth_year)
);

-- ============================
-- SCHOOLS OF THOUGHT
-- ============================
CREATE TABLE schools_of_thought (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    description TEXT,
    origin_century INT
);

-- ============================
-- THEMES
-- ============================
CREATE TABLE themes (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    description TEXT
);

-- ============================
-- WORKS
-- ============================
CREATE TABLE works (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    year INT,
    language VARCHAR(100),
    summary TEXT,
    philosopher_id BIGINT,
    country_id BIGINT,
    CONSTRAINT fk_work_philosopher FOREIGN KEY (philosopher_id) REFERENCES philosophers(id) ON DELETE SET NULL,
    CONSTRAINT fk_work_country FOREIGN KEY (country_id) REFERENCES countries(id) ON DELETE SET NULL
);

-- ============================
-- QUOTES
-- ============================
CREATE TABLE quotes (
    id BIGSERIAL PRIMARY KEY,
    content TEXT NOT NULL,
    language VARCHAR(50),
    philosopher_id BIGINT,
    work_id BIGINT,
    CONSTRAINT fk_quote_philosopher FOREIGN KEY (philosopher_id) REFERENCES philosophers(id) ON DELETE SET NULL,
    CONSTRAINT fk_quote_work FOREIGN KEY (work_id) REFERENCES works(id) ON DELETE SET NULL
);

-- ============================
-- INFLUENCES
-- ============================
CREATE TABLE influences (
    id BIGSERIAL PRIMARY KEY,
    influencer_id BIGINT NOT NULL,
    influenced_id BIGINT NOT NULL,
    strength VARCHAR(50) NOT NULL,
    CONSTRAINT fk_influencer FOREIGN KEY (influencer_id) REFERENCES philosophers(id) ON DELETE CASCADE,
    CONSTRAINT fk_influenced FOREIGN KEY (influenced_id) REFERENCES philosophers(id) ON DELETE CASCADE,
    CONSTRAINT check_influence_strength CHECK (strength IN ('STRONG', 'MEDIUM', 'WEAK'))
);

-- ============================
-- RELATION TABLES (M:N)
-- ============================

-- PHILOSOPHER ↔ SCHOOL
CREATE TABLE philosopher_school_of_thought (
    philosopher_id BIGINT NOT NULL,
    school_of_thought_id BIGINT NOT NULL,
    PRIMARY KEY (philosopher_id, school_of_thought_id),
    CONSTRAINT fk_pst_philosopher FOREIGN KEY (philosopher_id) REFERENCES philosophers(id) ON DELETE CASCADE,
    CONSTRAINT fk_pst_school FOREIGN KEY (school_of_thought_id) REFERENCES schools_of_thought(id) ON DELETE CASCADE
);

-- PHILOSOPHER ↔ THEME
CREATE TABLE philosopher_theme (
    philosopher_id BIGINT NOT NULL,
    theme_id BIGINT NOT NULL,
    PRIMARY KEY (philosopher_id, theme_id),
    CONSTRAINT fk_pt_philosopher FOREIGN KEY (philosopher_id) REFERENCES philosophers(id) ON DELETE CASCADE,
    CONSTRAINT fk_pt_theme FOREIGN KEY (theme_id) REFERENCES themes(id) ON DELETE CASCADE
);

-- WORK ↔ SCHOOL (CORRIGIDO para snake_case)
CREATE TABLE work_schools_of_thought (
    work_id BIGINT NOT NULL,
    school_of_thought_id BIGINT NOT NULL,
    PRIMARY KEY (work_id, school_of_thought_id),
    CONSTRAINT fk_ws_work FOREIGN KEY (work_id) REFERENCES works(id) ON DELETE CASCADE,
    CONSTRAINT fk_ws_school FOREIGN KEY (school_of_thought_id) REFERENCES schools_of_thought(id) ON DELETE CASCADE
);

-- WORK ↔ THEME
CREATE TABLE work_theme (
    work_id BIGINT NOT NULL,
    theme_id BIGINT NOT NULL,
    PRIMARY KEY (work_id, theme_id),
    CONSTRAINT fk_wt_work FOREIGN KEY (work_id) REFERENCES works(id) ON DELETE CASCADE,
    CONSTRAINT fk_wt_theme FOREIGN KEY (theme_id) REFERENCES themes(id) ON DELETE CASCADE
);

-- QUOTE ↔ THEME
CREATE TABLE quote_theme (
    quote_id BIGINT NOT NULL,
    theme_id BIGINT NOT NULL,
    PRIMARY KEY (quote_id, theme_id),
    CONSTRAINT fk_qt_quote FOREIGN KEY (quote_id) REFERENCES quotes(id) ON DELETE CASCADE,
    CONSTRAINT fk_qt_theme FOREIGN KEY (theme_id) REFERENCES themes(id) ON DELETE CASCADE
);

-- ============================
-- INDEXES
-- ============================
CREATE INDEX idx_philosophers_country ON philosophers(country_id);
CREATE INDEX idx_works_philosopher ON works(philosopher_id);
CREATE INDEX idx_works_country ON works(country_id);
CREATE INDEX idx_quotes_philosopher ON quotes(philosopher_id);
CREATE INDEX idx_quotes_work ON quotes(work_id);
CREATE INDEX idx_influences_influencer ON influences(influencer_id);
CREATE INDEX idx_influences_influenced ON influences(influenced_id);