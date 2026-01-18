
-- Movies
INSERT INTO movies (id, title, overview, runtime_minutes, release_date, rating) VALUES
(1, 'The Matrix', 'A hacker learns the world is a simulation and joins a rebellion.', 136, DATE '1999-03-31', 9),
(2, 'Inception', 'A thief enters dreams to steal secrets and plant an idea.', 148, DATE '2010-07-16', 8),
(3, 'Interstellar', 'Explorers travel through a wormhole to save humanity.', 169, DATE '2014-11-07', 9),
(4, 'The Godfather', 'A crime dynasty''s aging patriarch transfers control to his son.', 175, DATE '1972-03-24', 10),
(5, 'Parasite', 'A poor family schemes to become employed by a wealthy household.', 132, DATE '2019-05-30', 9),
(6, 'Whiplash', 'A drummer faces intense pressure from an elite music instructor.', 107, DATE '2014-10-10', 8);

-- Genres (ElementCollection -> join table)
INSERT INTO movie_genres (movie_id, genre) VALUES
(1, 'Action'),
(1, 'Sci-Fi'),
(2, 'Action'),
(2, 'Sci-Fi'),
(2, 'Thriller'),
(3, 'Sci-Fi'),
(3, 'Drama'),
(4, 'Crime'),
(4, 'Drama'),
(5, 'Drama'),
(5, 'Thriller'),
(6, 'Drama'),
(6, 'Music');


INSERT INTO users (id, username) VALUES
(1, 'user'),
(2, 'admin');
