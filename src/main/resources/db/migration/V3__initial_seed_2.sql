-- SEED COUNTRIES
INSERT INTO countries (name, start_year, end_year, region, continent)
VALUES
    ('France', 843, NULL, 'Western Europe', 'Europe'),
    ('England', 927, NULL, 'Western Europe', 'Europe'),
    ('Netherlands', 1581, NULL, 'Western Europe', 'Europe'),
    ('Italy', 1861, NULL, 'Southern Europe', 'Europe'),
    ('Denmark', 958, NULL, 'Northern Europe', 'Europe'),
    ('Austria', 976, NULL, 'Central Europe', 'Europe'),
    ('Switzerland', 1291, NULL, 'Central Europe', 'Europe');

-- SEED PHILOSOPHERS
INSERT INTO philosophers (name, birth_year, death_year, iep_link, spe_link, img, bio, era, country_id)
VALUES
    ('René Descartes', 1596, 1650,
     'https://iep.utm.edu/descarte/',
     'https://plato.stanford.edu/entries/descartes/',
     'https://iep.utm.edu/wp-content/media/descarte.jpg',
     'French philosopher, father of modern philosophy, famous for "Cogito, ergo sum".',
     'Modern',
     (SELECT id FROM countries WHERE name = 'France')),

    ('Georg Wilhelm Friedrich Hegel', 1770, 1831,
     'https://iep.utm.edu/hegel/',
     'https://plato.stanford.edu/entries/hegel/',
     'https://iep.utm.edu/wp-content/media/hegel.jpg',
     'German philosopher, key figure in German Idealism.',
     'Modern',
     (SELECT id FROM countries WHERE name = 'Germany')),

    ('Jean-Jacques Rousseau', 1712, 1778,
     'https://iep.utm.edu/rousseau/',
     'https://plato.stanford.edu/entries/rousseau/',
     'https://iep.utm.edu/wp-content/media/rousseau.jpg',
     'French philosopher, influential in political philosophy and education.',
     'Modern',
     (SELECT id FROM countries WHERE name = 'France')),

    ('Martin Heidegger', 1889, 1976,
     'https://iep.utm.edu/heidegge/',
     'https://plato.stanford.edu/entries/heidegger/',
     'https://iep.utm.edu/wp-content/media/heidegger.jpg',
     'German philosopher, founder of existential phenomenology.',
     'Contemporary',
     (SELECT id FROM countries WHERE name = 'Germany')),

    ('Friedrich Nietzsche', 1844, 1900,
     'https://iep.utm.edu/nietzsch/',
     'https://plato.stanford.edu/entries/nietzsche/',
     'https://iep.utm.edu/wp-content/media/nietzsch.jpg',
     'German philosopher, critic of morality and religion, known for the idea of the Übermensch.',
     'Contemporary',
     (SELECT id FROM countries WHERE name = 'Germany')),

    ('David Hume', 1711, 1776,
     'https://iep.utm.edu/hume/',
     'https://plato.stanford.edu/entries/hume/',
     'https://iep.utm.edu/wp-content/media/hume.jpg',
     'Scottish philosopher, major figure in empiricism and skepticism.',
     'Modern',
     (SELECT id FROM countries WHERE name = 'England')),

    ('John Locke', 1632, 1704,
     'https://iep.utm.edu/locke/',
     'https://plato.stanford.edu/entries/locke/',
     'https://iep.utm.edu/wp-content/media/locke.jpg',
     'English philosopher, father of liberalism.',
     'Modern',
     (SELECT id FROM countries WHERE name = 'England')),

    ('Thomas Hobbes', 1588, 1679,
     'https://iep.utm.edu/hobbes/',
     'https://plato.stanford.edu/entries/hobbes/',
     'https://iep.utm.edu/wp-content/media/hobbes.jpg',
     'English philosopher, known for his social contract theory.',
     'Modern',
     (SELECT id FROM countries WHERE name = 'England')),

    ('Baruch Spinoza', 1632, 1677,
     'https://iep.utm.edu/spinoza/',
     'https://plato.stanford.edu/entries/spinoza/',
     'https://iep.utm.edu/wp-content/media/spinoza.jpg',
     'Dutch philosopher, major rationalist of the 17th century.',
     'Modern',
     (SELECT id FROM countries WHERE name = 'Netherlands')),

    ('Michel Foucault', 1926, 1984,
     'https://iep.utm.edu/foucault/',
     'https://plato.stanford.edu/entries/foucault/',
     'https://iep.utm.edu/wp-content/media/foucault.jpg',
     'French philosopher, historian of systems of thought and power.',
     'Contemporary',
     (SELECT id FROM countries WHERE name = 'France')),

    ('Karl Marx', 1818, 1883,
     'https://iep.utm.edu/marx/',
     'https://plato.stanford.edu/entries/marx/',
     'https://iep.utm.edu/wp-content/media/marx.jpg',
     'German philosopher, economist, and revolutionary socialist.',
     'Contemporary',
     (SELECT id FROM countries WHERE name = 'Germany')),

    ('Søren Kierkegaard', 1813, 1855,
     'https://iep.utm.edu/kierkega/',
     'https://plato.stanford.edu/entries/kierkegaard/',
     'https://iep.utm.edu/wp-content/media/kierkega.jpg',
     'Danish philosopher, father of existentialism.',
     'Contemporary',
     (SELECT id FROM countries WHERE name = 'Denmark')),

    ('Ludwig Wittgenstein', 1889, 1951,
     'https://iep.utm.edu/wittgens/',
     'https://plato.stanford.edu/entries/wittgenstein/',
     'https://iep.utm.edu/wp-content/media/wittgenstein.jpg',
     'Austrian-British philosopher of language and mind.',
     'Contemporary',
     (SELECT id FROM countries WHERE name = 'Austria')),

    ('Augustine of Hippo', 354, 430,
     'https://iep.utm.edu/augustine/',
     'https://plato.stanford.edu/entries/augustine/',
     'https://iep.utm.edu/wp-content/media/augustine.jpg',
     'Christian theologian and philosopher of Late Antiquity.',
     'Medieval',
     (SELECT id FROM countries WHERE name = 'Italy')),

    ('Thomas Aquinas', 1225, 1274,
     'https://iep.utm.edu/aquinas/',
     'https://plato.stanford.edu/entries/aquinas/',
     'https://iep.utm.edu/wp-content/media/aquinas.jpg',
     'Italian philosopher and theologian, major figure in Scholasticism.',
     'Medieval',
     (SELECT id FROM countries WHERE name = 'Italy'));

-- SEED SCHOOLS OF THOUGHT
INSERT INTO schools_of_thought (name, description, origin_century)
VALUES
    ('Cartesianism', 'Rationalist philosophy based on Descartes.', 17),
    ('German Idealism', 'Philosophy developed by Kant, Fichte, Schelling, and Hegel.', 18),
    ('Social Contract Theory', 'Political philosophy on legitimacy of authority, Hobbes/Locke/Rousseau.', 17),
    ('Rationalism', 'Philosophy emphasizing reason as the chief source of knowledge.', 17),
    ('Empiricism', 'Philosophy emphasizing knowledge from experience, Hume/Locke.', 17),
    ('Marxism', 'Philosophy and political theory of Karl Marx.', 19),
    ('Existentialism', 'Philosophy focusing on individual freedom and responsibility.', 19),
    ('Phenomenology', 'Study of structures of consciousness, founded by Husserl and expanded by Heidegger.', 20),
    ('Post-Structuralism', 'Critical philosophy of language, discourse, and power, Foucault/Derrida.', 20),
    ('Scholasticism', 'Medieval system of theology and philosophy, dominated by Aquinas.', 13);

-- SEED WORKS
INSERT INTO works (title, year, summary, philosopher_id, country_id)
VALUES
    ('Meditations on First Philosophy', 1641, 'Descartes foundational work of modern philosophy.',
     (SELECT id FROM philosophers WHERE name = 'René Descartes'),
     (SELECT id FROM countries WHERE name = 'France')),

    ('Phenomenology of Spirit', 1807, 'Hegel''s major work on consciousness and dialectics.',
     (SELECT id FROM philosophers WHERE name = 'Georg Wilhelm Friedrich Hegel'),
     (SELECT id FROM countries WHERE name = 'Germany')),

    ('The Social Contract', 1762, 'Rousseau''s treatise on political authority and legitimacy.',
     (SELECT id FROM philosophers WHERE name = 'Jean-Jacques Rousseau'),
     (SELECT id FROM countries WHERE name = 'France')),

    ('Being and Time', 1927, 'Heidegger''s main work, foundation of existential phenomenology.',
     (SELECT id FROM philosophers WHERE name = 'Martin Heidegger'),
     (SELECT id FROM countries WHERE name = 'Germany')),

    ('Thus Spoke Zarathustra', 1883, 'Nietzsche''s philosophical novel on the Übermensch.',
     (SELECT id FROM philosophers WHERE name = 'Friedrich Nietzsche'),
     (SELECT id FROM countries WHERE name = 'Germany')),

    ('A Treatise of Human Nature', 1739, 'Hume''s work on human understanding and morality.',
     (SELECT id FROM philosophers WHERE name = 'David Hume'),
     (SELECT id FROM countries WHERE name = 'England')),

    ('Two Treatises of Government', 1689, 'Locke''s foundational text of liberal political theory.',
     (SELECT id FROM philosophers WHERE name = 'John Locke'),
     (SELECT id FROM countries WHERE name = 'England')),

    ('Leviathan', 1651, 'Hobbes''s work on social contract and state authority.',
     (SELECT id FROM philosophers WHERE name = 'Thomas Hobbes'),
     (SELECT id FROM countries WHERE name = 'England')),

    ('Ethics', 1677, 'Spinoza''s major work on God, nature, and human freedom.',
     (SELECT id FROM philosophers WHERE name = 'Baruch Spinoza'),
     (SELECT id FROM countries WHERE name = 'Netherlands')),

    ('Discipline and Punish', 1975, 'Foucault''s study on power and punishment in modern society.',
     (SELECT id FROM philosophers WHERE name = 'Michel Foucault'),
     (SELECT id FROM countries WHERE name = 'France')),

    ('Das Kapital', 1867, 'Marx''s analysis of capitalism and critique of political economy.',
     (SELECT id FROM philosophers WHERE name = 'Karl Marx'),
     (SELECT id FROM countries WHERE name = 'Germany')),

    ('Fear and Trembling', 1843, 'Kierkegaard''s reflection on faith and ethics.',
     (SELECT id FROM philosophers WHERE name = 'Søren Kierkegaard'),
     (SELECT id FROM countries WHERE name = 'Denmark')),

    ('Tractatus Logico-Philosophicus', 1921, 'Wittgenstein''s early work on language and logic.',
     (SELECT id FROM philosophers WHERE name = 'Ludwig Wittgenstein'),
     (SELECT id FROM countries WHERE name = 'Austria')),

    ('Confessions', 397, 'Augustine''s autobiographical work on his conversion.',
     (SELECT id FROM philosophers WHERE name = 'Augustine of Hippo'),
     (SELECT id FROM countries WHERE name = 'Italy')),

    ('Summa Theologica', 1274, 'Aquinas''s comprehensive work of theology and philosophy.',
     (SELECT id FROM philosophers WHERE name = 'Thomas Aquinas'),
     (SELECT id FROM countries WHERE name = 'Italy'));

-- SEED INFLUENCES
INSERT INTO influences (influencer_id, influenced_id, strength)
VALUES
    ((SELECT id FROM philosophers WHERE name = 'René Descartes'),
     (SELECT id FROM philosophers WHERE name = 'Baruch Spinoza'), 'STRONG'),

    ((SELECT id FROM philosophers WHERE name = 'René Descartes'),
     (SELECT id FROM philosophers WHERE name = 'John Locke'), 'STRONG'),

    ((SELECT id FROM philosophers WHERE name = 'John Locke'),
     (SELECT id FROM philosophers WHERE name = 'Jean-Jacques Rousseau'), 'MEDIUM'),

    ((SELECT id FROM philosophers WHERE name = 'Immanuel Kant'),
     (SELECT id FROM philosophers WHERE name = 'Georg Wilhelm Friedrich Hegel'), 'STRONG'),

    ((SELECT id FROM philosophers WHERE name = 'Georg Wilhelm Friedrich Hegel'),
     (SELECT id FROM philosophers WHERE name = 'Karl Marx'), 'STRONG'),

    ((SELECT id FROM philosophers WHERE name = 'Søren Kierkegaard'),
     (SELECT id FROM philosophers WHERE name = 'Friedrich Nietzsche'), 'STRONG'),

    ((SELECT id FROM philosophers WHERE name = 'Friedrich Nietzsche'),
     (SELECT id FROM philosophers WHERE name = 'Martin Heidegger'), 'STRONG'),

    ((SELECT id FROM philosophers WHERE name = 'Martin Heidegger'),
     (SELECT id FROM philosophers WHERE name = 'Michel Foucault'), 'STRONG'),

    ((SELECT id FROM philosophers WHERE name = 'Augustine of Hippo'),
     (SELECT id FROM philosophers WHERE name = 'Thomas Aquinas'), 'MEDIUM');
