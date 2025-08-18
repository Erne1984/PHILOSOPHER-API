-- SEED COUNTRIES
INSERT INTO countries (id, name, iso_Code, start_Year, end_Year, region)
VALUES
    (1, 'Ancient Greece', 'GR-ANC', -800, -146, 'Europe'),
    (2, 'Roman Empire', 'ROM', -27, 476, 'Europe'),
    (3, 'Germany', 'DE', 1871, NULL, 'Europe');

-- SEED PHILOSOPHERS
INSERT INTO philosophers (id, name, birth_Year, death_Year, bio, country_id)
VALUES
    (1, 'Socrates', -470, -399, 'Classical Greek philosopher credited as one of the founders of Western philosophy.', 1),
    (2, 'Plato', -428, -348, 'Student of Socrates and founder of the Academy in Athens.', 1),
    (3, 'Aristotle', -384, -322, 'Student of Plato and tutor of Alexander the Great.', 1),
    (4, 'Marcus Aurelius', 121, 180, 'Roman emperor and Stoic philosopher.', 2),
    (5, 'Immanuel Kant', 1724, 1804, 'German philosopher, central figure in modern philosophy.', 3);

-- SEED SCHOOLS OF THOUGHT
INSERT INTO schools_of_thought (id, name, description, origin_Century)
VALUES
    (1, 'Socratic Philosophy', 'Philosophy of questioning and ethics developed by Socrates.', -5),
    (2, 'Platonism', 'Philosophy based on the ideas of Plato.', -4),
    (3, 'Aristotelianism', 'Philosophy based on Aristotle’s works.', -4),
    (4, 'Stoicism', 'Hellenistic philosophy emphasizing virtue, wisdom, and self-control.', -3),
    (5, 'Kantianism', 'Philosophy of Immanuel Kant, emphasizing reason, autonomy, and morality.', 18);

-- SEED PHILOSOPHER_SCHOOL_OF_THOUGHT
INSERT INTO philosopher_school_of_thought (philosopher_id, school_of_thought_id)
VALUES
    (1, 1),
    (2, 2),
    (3, 3),
    (4, 4),
    (5, 5);

-- SEED WORKS
INSERT INTO works (id, title, year, summary, philosopher_id, country_id)
VALUES
    (1, 'Apology', -399, 'Defense speech of Socrates recorded by Plato.', 2, 1),
    (2, 'The Republic', -375, 'Plato’s dialogue on justice, politics, and the ideal state.', 2, 1),
    (3, 'Nicomachean Ethics', -350, 'Aristotle’s work on virtue and the good life.', 3, 1),
    (4, 'Meditations', 170, 'Personal writings of Marcus Aurelius reflecting Stoic philosophy.', 4, 2),
    (5, 'Critique of Pure Reason', 1781, 'Kant’s foundational work in epistemology and metaphysics.', 5, 3);

-- SEED THEMES
INSERT INTO themes (id, name, description)
VALUES
    (1, 'Ethics', 'Study of moral principles and values.'),
    (2, 'Metaphysics', 'Study of existence and reality.'),
    (3, 'Politics', 'Study of governance and justice.'),
    (4, 'Epistemology', 'Study of knowledge and belief.'),
    (5, 'Logic', 'Study of reasoning and arguments.');

-- SEED WORK_THEME
INSERT INTO work_theme (work_id, theme_id)
VALUES
    (1, 1),
    (2, 3),
    (2, 1),
    (3, 1),
    (3, 5),
    (4, 1),
    (5, 4),
    (5, 5);

-- SEED QUOTES
INSERT INTO quotes (id, content, philosopher_id, work_id)
VALUES
    (1, 'The unexamined life is not worth living.', 1, NULL),
    (2, 'Justice means minding your own business and not meddling with other men’s concerns.', 2, 2),
    (3, 'The good for man is an activity of the soul in accordance with virtue.', 3, 3),
    (4, 'You have power over your mind — not outside events. Realize this, and you will find strength.', 4, 4),
    (5, 'Thoughts without content are empty, intuitions without concepts are blind.', 5, 5);

-- SEED QUOTE_THEME
INSERT INTO quote_theme (quote_id, theme_id)
VALUES
    (1, 1),
    (2, 3),
    (3, 1),
    (4, 1),
    (5, 4);

-- SEED INFLUENCES
INSERT INTO influences (id, influencer_id, influenced_id, strength)
VALUES
    (1, 1, 2, 'STRONG'),
    (2, 2, 3, 'STRONG'),
    (3, 3, 4, 'MEDIUM'),
    (4, 4, 5, 'WEAK');