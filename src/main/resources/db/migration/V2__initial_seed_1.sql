
-- SEED COUNTRIES (sem IDs explícitos)
INSERT INTO countries (name, start_year, end_year, region, continent)
VALUES
    ('Ancient Greece', -800, -146, 'Southern Europe', 'Europe'),
    ('Roman Empire', -27, 476, 'Southern Europe', 'Europe'),
    ('Germany', 1871, NULL, 'Western Europe', 'Europe');

-- SEED PHILOSOPHERS (usando subqueries para country_id)
INSERT INTO philosophers (name, birth_year, death_year, iep_link, spe_link, img, bio, era, country_id)
VALUES
    ('Socrates', -470, -399,
     'https://iep.utm.edu/socrates/',
     'https://plato.stanford.edu/entries/socrates/',
     'https://iep.utm.edu/wp-content/media/Socrates2.png',
     'Classical Greek philosopher credited as one of the founders of Western philosophy.',
     'Antiquity',
     (SELECT id FROM countries WHERE name = 'Ancient Greece')),

    ('Plato', -428, -348,
     'https://iep.utm.edu/plato/',
     'https://plato.stanford.edu/entries/plato/',
     'https://iep.utm.edu/wp-content/media/plato.jpg',
     'Student of Socrates and founder of the Academy in Athens.',
     'Antiquity',
     (SELECT id FROM countries WHERE name = 'Ancient Greece')),

    ('Aristotle', -384, -322,
     'https://iep.utm.edu/aristotle/',
     'https://plato.stanford.edu/entries/aristotle/',
     'https://iep.utm.edu/wp-content/media/aristotle1.jpg',
     'Student of Plato and tutor of Alexander the Great.',
     'Antiquity',
     (SELECT id FROM countries WHERE name = 'Ancient Greece')),

    ('Marcus Aurelius', 121, 180,
     'https://iep.utm.edu/marcus-aurelius/',
     'https://plato.stanford.edu/entries/marcus-aurelius/',
     'https://iep.utm.edu/wp-content/media/aureliu.jpg',
     'Roman emperor and Stoic philosopher.',
     'Antiquity',
     (SELECT id FROM countries WHERE name = 'Roman Empire')),

    ('Immanuel Kant', 1724, 1804,
     'https://iep.utm.edu/kant/',
     'https://plato.stanford.edu/entries/kant/',
     'https://iep.utm.edu/wp-content/media/kant2.jpg',
     'German philosopher, central figure in modern philosophy.',
     'Modern',
     (SELECT id FROM countries WHERE name = 'Germany'));

-- SEED SCHOOLS OF THOUGHT
INSERT INTO schools_of_thought (name, description, origin_century)
VALUES
    ('Socratic Philosophy', 'Philosophy of questioning and ethics developed by Socrates.', -5),
    ('Platonism', 'Philosophy based on the ideas of Plato.', -4),
    ('Aristotelianism', 'Philosophy based on Aristotle''s works.', -4),
    ('Stoicism', 'Hellenistic philosophy emphasizing virtue, wisdom, and self-control.', -3),
    ('Kantianism', 'Philosophy of Immanuel Kant, emphasizing reason, autonomy, and morality.', 18);

-- SEED PHILOSOPHER_SCHOOL_OF_THOUGHT (com subqueries)
INSERT INTO philosopher_school_of_thought (philosopher_id, school_of_thought_id)
VALUES
    ((SELECT id FROM philosophers WHERE name = 'Socrates'),
     (SELECT id FROM schools_of_thought WHERE name = 'Socratic Philosophy')),

    ((SELECT id FROM philosophers WHERE name = 'Plato'),
     (SELECT id FROM schools_of_thought WHERE name = 'Platonism')),

    ((SELECT id FROM philosophers WHERE name = 'Aristotle'),
     (SELECT id FROM schools_of_thought WHERE name = 'Aristotelianism')),

    ((SELECT id FROM philosophers WHERE name = 'Marcus Aurelius'),
     (SELECT id FROM schools_of_thought WHERE name = 'Stoicism')),

    ((SELECT id FROM philosophers WHERE name = 'Immanuel Kant'),
     (SELECT id FROM schools_of_thought WHERE name = 'Kantianism'));

-- SEED THEMES
INSERT INTO themes (name, description)
VALUES
    ('Ethics', 'Study of moral principles and values.'),
    ('Metaphysics', 'Study of existence and reality.'),
    ('Politics', 'Study of governance and justice.'),
    ('Epistemology', 'Study of knowledge and belief.'),
    ('Logic', 'Study of reasoning and arguments.');

-- SEED WORKS (com subqueries)
INSERT INTO works (title, year, summary, philosopher_id, country_id)
VALUES
    ('Apology', -399, 'Defense speech of Socrates recorded by Plato.',
     (SELECT id FROM philosophers WHERE name = 'Plato'),
     (SELECT id FROM countries WHERE name = 'Ancient Greece')),

    ('The Republic', -375, 'Plato''s dialogue on justice, politics, and the ideal state.',
     (SELECT id FROM philosophers WHERE name = 'Plato'),
     (SELECT id FROM countries WHERE name = 'Ancient Greece')),

    ('Nicomachean Ethics', -350, 'Aristotle''s work on virtue and the good life.',
     (SELECT id FROM philosophers WHERE name = 'Aristotle'),
     (SELECT id FROM countries WHERE name = 'Ancient Greece')),

    ('Meditations', 170, 'Personal writings of Marcus Aurelius reflecting Stoic philosophy.',
     (SELECT id FROM philosophers WHERE name = 'Marcus Aurelius'),
     (SELECT id FROM countries WHERE name = 'Roman Empire')),

    ('Critique of Pure Reason', 1781, 'Kant''s foundational work in epistemology and metaphysics.',
     (SELECT id FROM philosophers WHERE name = 'Immanuel Kant'),
     (SELECT id FROM countries WHERE name = 'Germany'));

-- SEED WORK_THEME (com subqueries)
INSERT INTO work_theme (work_id, theme_id)
VALUES
    ((SELECT id FROM works WHERE title = 'Apology'),
     (SELECT id FROM themes WHERE name = 'Ethics')),

    ((SELECT id FROM works WHERE title = 'The Republic'),
     (SELECT id FROM themes WHERE name = 'Politics')),

    ((SELECT id FROM works WHERE title = 'The Republic'),
     (SELECT id FROM themes WHERE name = 'Ethics')),

    ((SELECT id FROM works WHERE title = 'Nicomachean Ethics'),
     (SELECT id FROM themes WHERE name = 'Ethics')),

    ((SELECT id FROM works WHERE title = 'Nicomachean Ethics'),
     (SELECT id FROM themes WHERE name = 'Logic')),

    ((SELECT id FROM works WHERE title = 'Meditations'),
     (SELECT id FROM themes WHERE name = 'Ethics')),

    ((SELECT id FROM works WHERE title = 'Critique of Pure Reason'),
     (SELECT id FROM themes WHERE name = 'Epistemology')),

    ((SELECT id FROM works WHERE title = 'Critique of Pure Reason'),
     (SELECT id FROM themes WHERE name = 'Logic'));

-- SEED QUOTES (com subqueries)
INSERT INTO quotes (content, language, philosopher_id, work_id)
VALUES
    ('The unexamined life is not worth living.', 'English',
     (SELECT id FROM philosophers WHERE name = 'Socrates'), NULL),

    ('Justice means minding your own business and not meddling with other men''s concerns.', 'English',
     (SELECT id FROM philosophers WHERE name = 'Plato'),
     (SELECT id FROM works WHERE title = 'The Republic')),

    ('The good for man is an activity of the soul in accordance with virtue.', 'English',
     (SELECT id FROM philosophers WHERE name = 'Aristotle'),
     (SELECT id FROM works WHERE title = 'Nicomachean Ethics')),

    ('You have power over your mind — not outside events. Realize this, and you will find strength.', 'English',
     (SELECT id FROM philosophers WHERE name = 'Marcus Aurelius'),
     (SELECT id FROM works WHERE title = 'Meditations')),

    ('Thoughts without content are empty, intuitions without concepts are blind.', 'English',
     (SELECT id FROM philosophers WHERE name = 'Immanuel Kant'),
     (SELECT id FROM works WHERE title = 'Critique of Pure Reason'));

-- SEED QUOTE_THEME (com subqueries)
INSERT INTO quote_theme (quote_id, theme_id)
VALUES
    ((SELECT id FROM quotes WHERE content LIKE 'The unexamined life%'),
     (SELECT id FROM themes WHERE name = 'Ethics')),

    ((SELECT id FROM quotes WHERE content LIKE 'Justice means minding%'),
     (SELECT id FROM themes WHERE name = 'Politics')),

    ((SELECT id FROM quotes WHERE content LIKE 'The good for man%'),
     (SELECT id FROM themes WHERE name = 'Ethics')),

    ((SELECT id FROM quotes WHERE content LIKE 'You have power over%'),
     (SELECT id FROM themes WHERE name = 'Ethics')),

    ((SELECT id FROM quotes WHERE content LIKE 'Thoughts without content%'),
     (SELECT id FROM themes WHERE name = 'Epistemology'));

-- SEED INFLUENCES (com subqueries)
INSERT INTO influences (influencer_id, influenced_id, strength)
VALUES
    ((SELECT id FROM philosophers WHERE name = 'Socrates'),
     (SELECT id FROM philosophers WHERE name = 'Plato'), 'STRONG'),

    ((SELECT id FROM philosophers WHERE name = 'Plato'),
     (SELECT id FROM philosophers WHERE name = 'Aristotle'), 'STRONG'),

    ((SELECT id FROM philosophers WHERE name = 'Aristotle'),
     (SELECT id FROM philosophers WHERE name = 'Marcus Aurelius'), 'MEDIUM'),

    ((SELECT id FROM philosophers WHERE name = 'Marcus Aurelius'),
     (SELECT id FROM philosophers WHERE name = 'Immanuel Kant'), 'WEAK');

-- RESET SEQUENCES (IMPORTANTE!)
SELECT setval('countries_id_seq', (SELECT MAX(id) FROM countries));
SELECT setval('philosophers_id_seq', (SELECT MAX(id) FROM philosophers));
SELECT setval('schools_of_thought_id_seq', (SELECT MAX(id) FROM schools_of_thought));
SELECT setval('themes_id_seq', (SELECT MAX(id) FROM themes));
SELECT setval('works_id_seq', (SELECT MAX(id) FROM works));
SELECT setval('quotes_id_seq', (SELECT MAX(id) FROM quotes));
SELECT setval('influences_id_seq', (SELECT MAX(id) FROM influences));