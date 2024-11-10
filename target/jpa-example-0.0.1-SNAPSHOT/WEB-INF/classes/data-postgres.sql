insert into addresses (street, city, postal_code) values ('Bulevar Cara Lazara 88', 'Novi Sad', 21000);
insert into addresses (street, city, postal_code) values ('Karadjordjeva 5A', 'Pozega', 31210);

insert into users (username, email, password, activated, name, surname, address_id, enabled, last_password_reset_date) values ('marko', 'marko@example.com', 'password123', true, 'Marko', 'Markovic', 1, true, '2017-10-01 21:58:58.508-07');
insert into users (username, email, password, activated, name, surname, address_id, enabled, last_password_reset_date) values ('pera', 'pera@gmail.com', '$2a$10$phbu2oHcLQFL4PoXYyRW/erd.WJsHr9oAkuMxk0MoBdNjZ02Evxm2', true , 'pera', 'peric', 2, true, '2017-10-01 21:58:58.508-07');

INSERT INTO ROLE (name) VALUES ('ROLE_USER');
INSERT INTO ROLE (name) VALUES ('ROLE_ADMIN');

INSERT INTO USER_ROLE (user_id, role_id) VALUES (1, 1);
INSERT INTO USER_ROLE (user_id, role_id) VALUES (2, 1);
INSERT INTO USER_ROLE (user_id, role_id) VALUES (2, 2);

insert into users (username, email, password, activated, name, surname, address_id) values ('marko', 'marko@example.com', 'password123', true, 'Marko', 'Markovic', 1);
insert into users (username, email, password, activated, name, surname, address_id) values ('pera', 'pera@gmail.com', '$2a$10$phbu2oHcLQFL4PoXYyRW/erd.WJsHr9oAkuMxk0MoBdNjZ02Evxm2', true , 'pera', 'peric', 2);

insert into posts (user_id, description, creation_time, image_path, location_id)
values (
           1,
           'This is a sample post description.',
           '2024-11-07 12:30:00',
           '/images/sample.jpg',
           1
       );

insert into posts (user_id, description, creation_time, image_path, location_id)
values (
           2,
           'This is a sample post description 2.',
           '2024-11-07 12:30:00',
           '/images/sample2.jpg',
           1
       );

insert into likes(user_id, post_id) values  (1,2);
insert into likes(user_id, post_id) values  (2,1);

insert into comments(text, creation_time, user_id, post_id) values ('komentar','2024-11-07 12:30:00',1,1);

