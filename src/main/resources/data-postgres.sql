INSERT INTO users (username, email, password, activated) VALUES ('marko', 'marko@example.com', 'password123', true);
INSERT INTO users (username, email, password, activated) VALUES ('milan', 'milan@example.com', 'password456', true);
INSERT INTO users (username, email, password, activated) VALUES ('ivana', 'ivana@example.com', 'password789', false);
INSERT INTO users (username, email, password, activated) VALUES ('bojan', 'bojan@example.com', 'bojanpass', true);
INSERT INTO users (username, email, password, activated) VALUES ('pera', 'pera@example.com', 'perapass', false);
INSERT INTO users (username, email, password, activated) VALUES ('zoran', 'zoran@example.com', 'zoranpass', true);
INSERT INTO users (username, email, password, activated) VALUES ('bojana', 'bojana@example.com', 'bojanapass', false);
INSERT INTO users (username, email, password, activated) VALUES ('milana', 'milana@example.com', 'milanapass', true);
INSERT INTO users (username, email, password, activated) VALUES ('jovana', 'jovana@example.com', 'jovanapass', false);

INSERT INTO post (description, image_url, like_count) VALUES ('Ovo je prvi post!', 'http://example.com/image1.jpg', 10);
INSERT INTO post (description, image_url, like_count) VALUES ('Drugi post sa divnom slikom.', 'http://example.com/image2.jpg', 5);
INSERT INTO post (description, image_url, like_count) VALUES ('Vreme je za učenje!', 'http://example.com/image3.jpg', 20);
INSERT INTO post (description, image_url, like_count) VALUES ('Podeli svoje misli!', 'http://example.com/image4.jpg', 15);
INSERT INTO post (description, image_url, like_count) VALUES ('Kako napreduješ sa projektom?', 'http://example.com/image5.jpg', 8);

INSERT INTO comments (text, post_id, user_id, created_at) VALUES ('Odličan post!', 1, 1, NOW());  -- Marko
INSERT INTO comments (text, post_id, user_id, created_at) VALUES ('Slažem se sa tobom!', 1, 2, NOW());  -- Milan
INSERT INTO comments (text, post_id, user_id, created_at) VALUES ('Divna slika!', 2, 3, NOW());  -- Ivana
INSERT INTO comments (text, post_id, user_id, created_at) VALUES ('Učenje nikad ne prestaje.', 3, 4, NOW());  -- Bojan
INSERT INTO comments (text, post_id, user_id, created_at) VALUES ('Svaka čast na radu!', 4, 5, NOW());  -- Pera
INSERT INTO comments (text, post_id, user_id, created_at) VALUES ('Vreme je da se krene!', 5, 6, NOW());  -- Zoran
INSERT INTO comments (text, post_id, user_id, created_at) VALUES ('Kako ide projekat?', 5, 7, NOW());  -- Bojana
