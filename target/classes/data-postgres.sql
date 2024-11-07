insert into users (username, email, password, activated, name) values ('marko', 'marko@example.com', 'password123', true, 'Marko');


INSERT INTO post (description, image_url, like_count) VALUES ('Ovo je prvi post!', 'http://example.com/image1.jpg', 10);
INSERT INTO post (description, image_url, like_count) VALUES ('Drugi post sa divnom slikom.', 'http://example.com/image2.jpg', 5);
INSERT INTO post (description, image_url, like_count) VALUES ('Vreme je za učenje!', 'http://example.com/image3.jpg', 20);
INSERT INTO post (description, image_url, like_count) VALUES ('Podeli svoje misli!', 'http://example.com/image4.jpg', 15);
INSERT INTO post (description, image_url, like_count) VALUES ('Kako napreduješ sa projektom?', 'http://example.com/image5.jpg', 8);

INSERT INTO comments (text, post_id, user_id, created_at) VALUES ('Odličan post!', 1, 1, NOW());  -- Marko
