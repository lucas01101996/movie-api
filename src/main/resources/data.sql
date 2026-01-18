INSERT INTO movies (title, overview, runtime_minutes, release_date, rating) VALUES
('The Matrix', 'A hacker learns the world is a simulation and joins a rebellion.', 136, DATE '1999-03-31', 9),
('Inception', 'A thief enters dreams to steal secrets and plant an idea.', 148, DATE '2010-07-16', 8),
('Interstellar', 'Explorers travel through a wormhole to save humanity.', 169, DATE '2014-11-07', 9),
('The Godfather', 'A crime dynasty''s aging patriarch transfers control to his son.', 175, DATE '1972-03-24', 10),
('Parasite', 'A poor family schemes to become employed by a wealthy household.', 132, DATE '2019-05-30', 9),
('Whiplash', 'A drummer faces intense pressure from an elite music instructor.', 107, DATE '2014-10-10', 8);

INSERT INTO movie_genres (movie_id, genre) VALUES
((SELECT id FROM movies WHERE title = 'The Matrix'), 'Action'),
((SELECT id FROM movies WHERE title = 'The Matrix'), 'Sci-Fi'),

((SELECT id FROM movies WHERE title = 'Inception'), 'Action'),
((SELECT id FROM movies WHERE title = 'Inception'), 'Sci-Fi'),
((SELECT id FROM movies WHERE title = 'Inception'), 'Thriller'),

((SELECT id FROM movies WHERE title = 'Interstellar'), 'Sci-Fi'),
((SELECT id FROM movies WHERE title = 'Interstellar'), 'Drama'),

((SELECT id FROM movies WHERE title = 'The Godfather'), 'Crime'),
((SELECT id FROM movies WHERE title = 'The Godfather'), 'Drama'),

((SELECT id FROM movies WHERE title = 'Parasite'), 'Drama'),
((SELECT id FROM movies WHERE title = 'Parasite'), 'Thriller'),

((SELECT id FROM movies WHERE title = 'Whiplash'), 'Drama'),
((SELECT id FROM movies WHERE title = 'Whiplash'), 'Music');

INSERT INTO users (username) VALUES
('user'),
('admin');